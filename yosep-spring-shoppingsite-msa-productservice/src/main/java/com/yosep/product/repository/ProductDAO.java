package com.yosep.product.repository;

import java.util.List;

import com.yosep.product.entity.Product;

public interface ProductDAO {
	// 01. 물품 목록
	public List<Product> productList(int productCode);
	
	// 02. 물품 생성
	public int createProduct(Product product);
	
	// 03. 물품 상세 보기
	public Product viewProduct(String productId);
	
	// 04. 물품 삭제
	public void deleteProduct(String productId);
	
	// 05. 물품 수정
	public void updateProduct(Product product);
	
	// 06. 물품 프로필 이미지 정보 생성
	public int createProductProfileImg(Product product,String url);
	
	// 07. 물품 설명 정보 생성
	public int createProductDescription(Product product,String productDescription, String url);
	
	// 08. 물품 갯수 가져오기
	public int getProductsCount(String productType);
	
	// 09. 게시판용 물품 목록
	public List<Product> productAll(int start, int end ,String productType, String keyword);
	
	// 해당 물품의 대표이미지 가져오기
	public String getProductProfileImageURL(String productId);
	
	// 물품의 상세정보 가져오기
	public Product getProductDetail(String productId);
	
	// 물품 수량 업데이트
	public void setProductQuantity(String productId);
}
