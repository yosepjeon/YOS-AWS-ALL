package com.yosep.website.client;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yosep.website.component.FileComponent;
import com.yosep.website.entity.Product;
import com.yosep.website.entity.ProductDescription;
import com.yosep.website.entity.ProductRow;
import com.yosep.website.fileIO.S3Uploader;

@CrossOrigin
@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Value("file.upload-dir")
	private static String root;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private FileComponent fileComponent;
	@Autowired
	private S3Uploader s3Uploader;

//	@Autowired
//	private FileComponentS3 fileComponent;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/product/{type}/{detailType}", method = RequestMethod.GET)
	public ModelAndView showProductPage(@PathVariable("type") String type,
			@PathVariable("detailType") String detailType, @RequestParam(value="curPage",defaultValue = "1") int curPage) {
		ModelAndView mav = new ModelAndView();
//		List<Integer> itemRows = new ArrayList<>();
//		itemRows.add(1);
//		itemRows.add(2);
//		itemRows.add(3);
		
//		int productRecordsCount = restTemplate.getForObject("http://product-apigateway/api/product/getProductsCount?curPage=" + curPage + "&productType=" + detailType, int.class);
//		Product[] productArray = restTemplate.getForObject("http://product-apigateway/api/product/getProducts?curPage=" + curPage + "&productType=" + detailType, Product[].class);
//		List<Product> productsRow1 = Arrays.asList(productArray[0],productArray[1],productArray[2]);
//		List<Product> productsRow2 = Arrays.asList(productArray[3],productArray[4],productArray[5]);
//		List<Product> productsRow3 = Arrays.asList(productArray[6],productArray[7],productArray[8]);
		Map<String, Object> productMap = restTemplate.getForObject("http://product-apigateway/api/product/getProducts?curPage=" + curPage + "&productType=" + detailType, Map.class);
		ObjectMapper mapper = new ObjectMapper();
		List<Product> products = mapper.convertValue(productMap.get("products"), new TypeReference<List<Product>>() {});
		List<Product> productsRow1 = new ArrayList<>();
		List<Product> productsRow2 = new ArrayList<>();
		List<Product> productsRow3 = new ArrayList<>();
		
		List<ProductRow> itemRows = new ArrayList<>();
		int count = 0;
		for(Product p : products) {
//			System.out.println(p.getProductProfileImageURL());
			if(count/3 == 0) {
				productsRow1.add(p);
			}else if(count/3 == 1) {
				productsRow2.add(p);
			}else if(count/3 == 2) {
				productsRow3.add(p);
			}
			
			count++;
		}
		
		itemRows.add(new ProductRow(productsRow1));
		itemRows.add(new ProductRow(productsRow2));
		itemRows.add(new ProductRow(productsRow3));
		
//		System.out.println("@@@@" + itemRows.get(0).items.get(0).getProductProfileImageURLs().get(0));
		
		mav.addObject("itemRows", itemRows);
		mav.addObject("productMap",productMap);
		mav.addObject("type", type);
		mav.addObject("detailType", detailType);
		mav.setViewName("product");
		return mav;
	}

	@RequestMapping(value = "/detail_product/{type}/{detailType}", method = RequestMethod.GET)
	public ModelAndView showProductDetailPage(@PathVariable("type") String type,
			@PathVariable("detailType") String detailType,@RequestParam(value="productId")String productId,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Product product = restTemplate.getForObject("http://product-apigateway/api/product/getProductDetail?productId=" + productId, Product.class);
		
		mav.addObject("userId",(String)session.getAttribute("userId"));
		mav.addObject("product",product);
		mav.addObject("type",type);
		mav.addObject("detailType",detailType);
		mav.setViewName("detail_product");
		return mav;
	}

	@RequestMapping(value = "/insert_product/{type}/{detailType}", method = RequestMethod.GET)
	public ModelAndView showProductInsertPage(@PathVariable("type") String type,
			@PathVariable("detailType") String detailType) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("type", type);
		mav.addObject("detailType", detailType);
		mav.setViewName("insert_product");

		return mav;
	}

	@RequestMapping(value = "/createProduct/{type}/{detailType}", method = RequestMethod.POST)
	public String createProduct(@PathVariable("type") String type, @PathVariable("detailType") String detailType,
			@RequestParam("productId") String productId, @RequestParam("productName") String productName,
			@RequestParam("productSale") String productSale, @RequestParam("productDetail") String productDetail,
			@RequestParam("productPrice") String productPrice, @RequestParam("productQuantity") String productQuantity,
			@RequestParam("productProfileImageFiles[]") MultipartFile[] productProfileImageFiles) throws Exception {
//			@RequestParam("productDescription1_file") MultipartFile productDescription1_file,
//			@RequestParam("productDescription2_file") MultipartFile productDescription2_file,
//			@RequestParam("productDescription3_file") MultipartFile productDescription3_file,
//			@RequestParam("productDescription1") String productDescription1,
//			@RequestParam("productDescription2") String productDescription2,
//			@RequestParam("productDescription3") String productDescription3) throws FileUploadException {
		System.out.println("call create!!!");

//		Product product = Product.builder().productId(productId).productName(productName).productPrice(Integer.valueOf(productPrice))
//				.productDetail(productDetail).productSale(productSale.equals("") ? 0 : Integer.valueOf(productSale)).productQuantity(Integer.valueOf(productQuantity))
//				.productDescriptions(new ArrayList<ProductDescription>())
//				.productProfileImageURLs(new ArrayList<String>()).build();
		Product product = new Product();
		product.setProductId(productId);
		product.setProductName(productName);
		product.setProductType(detailType);
		product.setProductPrice(Integer.valueOf(productPrice));
		product.setProductSale(productSale.equals("") ? 0 : Integer.valueOf(productSale));
		product.setProductDetail(productDetail);
		product.setProductQuantity(Integer.valueOf(productQuantity));
		product.setProductDescriptions(new ArrayList<ProductDescription>());
		product.setProductProfileImageURLs(new ArrayList<String>());

		ArrayList<MultipartFile> productDescriptionFileArray = new ArrayList<>();
		String storeDescriptionFileURL;
//		if (!productDescription1_file.isEmpty()) {
//			String fileType = productDescription1_file.getOriginalFilename()
//					.substring(productDescription1_file.getOriginalFilename().lastIndexOf("."));
//			storeDescriptionFileURL = root + "/" + type + "/" + detailType + "/description/" + product.getProductId()
//					+ fileType;
//			product.getProductDescriptions().add(new ProductDescription(productDescription1, storeDescriptionFileURL));
//		}
//
//		if (!productDescription2_file.isEmpty()) {
//			String fileType = productDescription2_file.getOriginalFilename()
//					.substring(productDescription2_file.getOriginalFilename().lastIndexOf("."));
//			storeDescriptionFileURL = root + "/" + type + "/" + detailType + "/description/" + product.getProductId()
//					+ fileType;
//			product.getProductDescriptions().add(new ProductDescription(productDescription2, storeDescriptionFileURL));
//		}
//
//		if (!productDescription3_file.isEmpty()) {
//			String fileType = productDescription3_file.getOriginalFilename()
//					.substring(productDescription3_file.getOriginalFilename().lastIndexOf("."));
//			storeDescriptionFileURL = root + "/" + type + "/" + detailType + "/description/" + product.getProductId()
//					+ fileType;
//			product.getProductDescriptions().add(new ProductDescription(productDescription3, storeDescriptionFileURL));
//		}

		System.out.println("productProfileImageFilesSize:" + productProfileImageFiles.length);
		for (MultipartFile m : productProfileImageFiles) {
//			fileComponent.storeProductProfileImageFiles(product, m, type, detailType);
			s3Uploader.upload(product,m, "yosep-spring-shoppingsite-msa-product" + File.separator + type + File.separator + detailType + File.separator + product.getProductId());
		}

//		System.out.println("productDescriptionFileArraySize:" + productDescriptionFileArray.size());
//		for (MultipartFile m : productDescriptionFileArray) {
//			fileComponent.storeProductDescriptionImageFiles(product, m, type, detailType);
//		}

		int status = restTemplate.postForObject(
				"http://product-apigateway/api/product/" + type + "/" + detailType + "/create", product, int.class);

		if (status == 200) {
			return "main";
		} else {
			return null;
		}
	}
}
