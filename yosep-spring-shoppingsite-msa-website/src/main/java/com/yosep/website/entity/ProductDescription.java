package com.yosep.website.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
//@Value
public class ProductDescription {
	private String productDescription;
	private String url;
	
	public ProductDescription(String productDescription,String url) {
		this.productDescription = productDescription;
		this.url = url;
	}
}
