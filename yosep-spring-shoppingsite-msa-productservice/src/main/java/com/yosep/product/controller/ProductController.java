package com.yosep.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yosep.product.component.ProductComponent;
import com.yosep.product.entity.Product;
import com.yosep.product.entity.ProductPager;

@RestController
@CrossOrigin
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductComponent productComponent;

	@RequestMapping(value = "/{type}/{detailType}/create", method = RequestMethod.POST)
	public int createProduct(@PathVariable("type") String type, @PathVariable("detailType") String detailType,
			@RequestBody Product product) {
		productComponent.createProduct(product);
		
		return 200;
	}
	
	@RequestMapping(value="/getProductsCount")
	public int getProductsCount(@RequestParam("curPage") int currentPage,@RequestParam("productType")String productType) {
		int productsCount = productComponent.getProductsCount(productType);
		System.out.println(productsCount + "####");
		
		return productsCount;
	}
	
	@RequestMapping(value="/getProducts")
	public Map<String,Object> getProducts(@RequestParam(defaultValue="1",value="curPage") int curPage, @RequestParam("productType")String productType) throws Exception{
//		List<Product> products = productComponent.productAll(start, end, searchOption, keyword)
		int count = productComponent.getProductsCount(productType);
		
		ProductPager productPager = new ProductPager(count, curPage);
		int start = productPager.getPageBegin();
		int end = productPager.getPageEnd();
		
		List<Product> products = productComponent.productAll(start, end, productType, "");
//		System.out.println(products.size());
//		System.out.println("start: " + start);
//		System.out.println("end: " + end);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("products", products);
		map.put("count", count);
		map.put("keyword", "");
		map.put("productPager", productPager);
		
		return map;
	}
	
	@RequestMapping(value="/getProductDetail")
	public Product getProductDetail(@RequestParam(value="productId")String productId) {
		return productComponent.getProductDetail(productId);
	}
	
	@RequestMapping(value="/getProductByOrder")
	public Product getProductsByOrder(@RequestParam("productId")String productId) {
		
		return productComponent.getProductDetail(productId);
	}
}
