package com.yosep.product.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	private String productId;
	private String productName;
	private int productSale;
	private String productDetail;
	private int productPrice;
	private int productQuantity;
	private String productType;
//	private MultipartFile[] productProfileImageFiles;
//	private ArrayList<MultipartFile> productDescription_files;
	private List<ProductDescription> productDescriptions;
//	private ArrayList<String> productDescriptions;
	private List<String> productProfileImageURLs;
	private String productProfileImageURL;
//	private ArrayList<String> productDescription_filesURLs;
}
