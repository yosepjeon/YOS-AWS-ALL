package com.yosep.product.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yosep.product.entity.Product;
import com.yosep.product.entity.ProductDescription;
import com.yosep.product.repository.ProductDAO;

@RefreshScope
@Component
public class ProductComponent {
	@Autowired
	ProductDAO productDAO;

	public ProductComponent() {

	}

	@Transactional
	public void createProduct(Product product) {
		int createProductResult = productDAO.createProduct(product);

		List<String> ppList = product.getProductProfileImageURLs();
		for (String url : ppList) {
			int createProfileFiles = productDAO.createProductProfileImg(product,url);
		}
		
		List<ProductDescription> pdList = product.getProductDescriptions();
		for (ProductDescription pd : pdList) {
			int createDescriptionFiles = productDAO.createProductDescription(product,pd.getProductDescription(),pd.getUrl());
		}
	}
	
	@Transactional
	public List<Product> productAll(int start, int end, String productType, String keyword) throws Exception {
		List<Product> productList = productDAO.productAll(start, end, productType, keyword);
		for(Product p : productList) {
			List<String> productUrl = new ArrayList<>();
			String url = productDAO.getProductProfileImageURL(p.getProductId());
			productUrl.add(url);
			p.setProductProfileImageURLs(productUrl);
			p.setProductProfileImageURL(url);
		}
		return productList;
	}
	
	public int getProductsCount(String productType) {
		int productsCount = productDAO.getProductsCount(productType);
		
		return productsCount;
	}
	
	public Product getProductDetail(String productId) {
		Product product = productDAO.getProductDetail(productId);
		String url = productDAO.getProductProfileImageURL(productId);
		product.setProductProfileImageURL(url);
		
		return product;
	}
}
