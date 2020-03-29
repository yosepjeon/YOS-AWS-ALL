package com.yosep.website.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yosep.website.entity.Order;
import com.yosep.website.entity.Product;
import com.yosep.website.order.OrderUtil;

@CrossOrigin
@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	OrderUtil orderUtil;
	@Autowired
	private RestTemplate restTemplate;
	

	@RequestMapping("/order/{type}/{detailType}/{productId}")
	public ModelAndView showOrderPage(@PathVariable("type") String type, @PathVariable("detailType") String detailType,
			@PathVariable("productId") String productId, HttpSession session) {
		Product product = restTemplate.getForObject(
				"http://product-apigateway/api/product/getProductDetail?productId=" + productId, Product.class);
//		System.out.println(session.getAttribute("userId"));
//		System.out.println(session.getAttribute("userName"));

		UUID uuid = UUID.randomUUID();
		String userId = (String) session.getAttribute("userId");

		Order order = new Order();
		order.setOrderId(productId + userId + uuid);
		order.setSenderId((String) session.getAttribute("userId"));
		order.setSenderName((String) session.getAttribute("userName"));
		order.setProductId(product.getProductId());
		order.setBuy(false);

		int httpStatus = restTemplate.postForObject("http://order-apigateway/api/order/createOrder", order, int.class);

		ModelAndView mav = new ModelAndView();

		mav.addObject("userId", userId);
		mav.addObject("product", product);
		mav.addObject("userName", session.getAttribute("userName"));
		mav.setViewName("afterAddToCart");

		return mav;
	}

	@RequestMapping("/cart")
	public ModelAndView showCartPage(@RequestParam("userId") String userId) {
		ModelAndView mav = new ModelAndView();
//		ObjectMapper om = new ObjectMapper();
//		Object orderList = restTemplate.getForObject("http://order-apigateway/api/order/getUsersOrders?userId=" + userId, Object.class);
//		List<Order> orders = om.convertValue(orderList, new TypeReference<List<Order>>() {});
		List<Order> orders = orderUtil.getOrderListBeforeBuy(userId, restTemplate);
		List<Product> products = new ArrayList<Product>();
		int totalPrice = 0;

		for (Order order : orders) {
			Product product = restTemplate.getForObject(
					"http://product-apigateway/api/product/getProductByOrder?productId=" + order.getProductId(),
					Product.class);
			products.add(product);
			totalPrice += (int) (product.getProductPrice() * (1.0 - (double) product.getProductSale() / 100));
		}

		mav.addObject("products", products);
		mav.addObject("totalPrice", totalPrice);
		mav.setViewName("checkout_cart");
		return mav;
	}

	@RequestMapping(value = "/pay/{productId}")
	public ModelAndView showPayPage(@PathVariable("productId") String productId,
			@RequestParam("senderId") String senderId, @RequestParam("senderName") String senderName,
			@RequestParam("receiverName") String receiverName, @RequestParam("phone") String phone,
			@RequestParam("postCode") String postCode, @RequestParam("roadAddr") String roadAddr,
			@RequestParam("jibunAddr") String jibunAddr, @RequestParam("extraAddr") String extraAddr,
			@RequestParam("detailAddr") String detailAddr, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userId = (String) session.getAttribute("userId");
//		Product product = restTemplate.getForObject("http://product-apigateway/api/product/getProductDetail?productId=" + productId, Product.class);
//		mav.addObject("order",order);
		Order orderForPost = new Order();
		orderForPost.setReceiverName(receiverName);
		orderForPost.setPhone(phone);
		orderForPost.setPostCode(postCode);
		orderForPost.setRoadAddr(roadAddr);
		orderForPost.setJibunAddr(jibunAddr);
		orderForPost.setExtraAddr(extraAddr);
		orderForPost.setDetailAddr(detailAddr);
		
		List<Order> orders = orderUtil.getOrderListBeforeBuy(userId, restTemplate);

		List<Product> products = new ArrayList<Product>();
		int totalPrice = 0;

		for (Order order : orders) {
			int result = restTemplate.postForObject("http://order-apigateway/api/order/setOrderElements", orderForPost, int.class);
			
			Product product = restTemplate.getForObject(
					"http://product-apigateway/api/product/getProductByOrder?productId=" + order.getProductId(),
					Product.class);
			products.add(product);
			totalPrice += (int) (product.getProductPrice() * (1.0 - (double) product.getProductSale() / 100));
		}

		mav.addObject("productName", products.get(0).getProductName());
		mav.addObject("productCount", products.size());
		mav.addObject("products", products);
		mav.addObject("totalPrice", totalPrice);
		mav.addObject("senderName", senderName);
		mav.addObject("phone", phone);
		mav.addObject("postCode", postCode);
		mav.addObject("roadAddr", roadAddr);
		mav.addObject("userId", userId);
		mav.setViewName("pay");

		return mav;
	}

	@RequestMapping("/checkout_address")
	public ModelAndView showCheckOutAddress(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("checkout_address");
		mav.addObject("userName", session.getAttribute("userName"));
		return mav;
	}

	@RequestMapping(value = "/processPay/{userId}")
	public ModelAndView processPay(@PathVariable("userId") String userId) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String,String>();
		parameters.add("userId", userId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(parameters,headers);
		
		int isSuccess = (int)restTemplate.postForObject("http://order-apigateway/api/order/updateOrder", request, int.class);
		
		
		
		ModelAndView mav = new ModelAndView();
		ObjectMapper om = new ObjectMapper();
		List<Order> orders = orderUtil.getOrderListAfterBuy(userId, restTemplate);
		List<Product> products = new ArrayList<>();
		
		int totalPrice = 0;

		for (Order order : orders) {
			Product product = restTemplate.getForObject(
					"http://product-apigateway/api/product/getProductByOrder?productId=" + order.getProductId(),
					Product.class);
			products.add(product);
			totalPrice += (int) (product.getProductPrice() * (1.0 - (double) product.getProductSale() / 100));
		}
		
		
		mav.addObject("products", products);
		mav.addObject("totalPrice", totalPrice);
		mav.setViewName("checkout_pay_success");

		return mav;
	}
}

//@Component
//@Lazy
//class OrderSender {
//	RabbitMessagingTemplate template;
//	
//	@Autowired
//	public OrderSender(RabbitMessagingTemplate template) {
//		// TODO Auto-generated constructor stub
//		this.template = template;
//	}
//	
//	@Bean
//	Queue queue() {
//		return new Queue("OrderQ", false);
//	}
//	
//	public void send(String userId) {
//		template.convertAndSend("OrderQ",userId);
//	}
//}

//@Component
//@Lazy
//class ProductSender {
//	RabbitMessagingTemplate template;
//	
//	@Autowired
//	public ProductSender(RabbitMessagingTemplate template) {
//		// TODO Auto-generated constructor stub
//		this.template = template;
//	}
//	
//	@Bean
//	Queue queue() {
//		return new Queue("ProductQ", false);
//	}
//	
//	public void send(String productId) {
//		template.convertAndSend("productId");
//	}
//}
