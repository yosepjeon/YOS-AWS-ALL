package com.yosep.website.order;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yosep.website.entity.Order;

@Component
public class OrderUtil {
	public List<Order> orderList;
	
	public List<Order> getOrderListBeforeBuy(String userId,RestTemplate restTemplate) {
		ObjectMapper om = new ObjectMapper();
		Object orderList = restTemplate.getForObject("http://order-apigateway/api/order/getUsersOrdersBeforeBuy?userId=" + userId, Object.class);
		List<Order> orders = om.convertValue(orderList, new TypeReference<List<Order>>() {});
		
		return orders;
	}
	
	public List<Order> getOrderListAfterBuy(String userId,RestTemplate restTemplate) {
		ObjectMapper om = new ObjectMapper();
		Object orderList = restTemplate.getForObject("http://order-apigateway/api/order/getUsersOrdersAfterBuy?userId=" + userId, Object.class);
		List<Order> orders = om.convertValue(orderList, new TypeReference<List<Order>>() {});
		
		return orders;
	}
}
