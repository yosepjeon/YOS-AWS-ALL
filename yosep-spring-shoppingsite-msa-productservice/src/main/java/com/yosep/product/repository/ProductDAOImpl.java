package com.yosep.product.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yosep.product.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {
	static private final String NAMESPACE = "com.yosep.product.mapper.productMapper";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Product> productList(int productCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createProduct(Product product) {
		// TODO Auto-generated method stub
		System.out.println(product.toString());
		return sqlSession.insert(NAMESPACE + ".createProduct", product);
	}

	@Override
	public Product viewProduct(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(String productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public int createProductProfileImg(Product product, String url) {
		// TODO Auto-generated method stub
		String fileName = url.substring(url.lastIndexOf(".") + 1);
		sqlSession.insert(NAMESPACE + ".createProductProfileImg", new ProductProfileImgElement(product.getProductId(), fileName, url));
		return 0;
	}

	@Override
	public int createProductDescription(Product product, String productDescription, String url) {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".createProductDescription",
				new ProductDescriptionElement(product.getProductId(), productDescription, url));
		return 0;
	}
	
	@Override
	public int getProductsCount(String productType) {
		// TODO Auto-generated method stub
		int productsCount = sqlSession.selectOne(NAMESPACE + ".getProductsCount", productType);
		
		return productsCount;
	}
	
	@Override
	public List<Product> productAll(int start, int end, String productType, String keyword) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("productType", productType);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList(NAMESPACE + ".productAll",map);
	}
	
	@Override
	public String getProductProfileImageURL(String productId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".getProductProfileImage",productId);
	}
	
	@Override
	public Product getProductDetail(String productId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".getProductDetail",productId);
	}
	
	@Override
	public void setProductQuantity(String productId) {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".updateProductQuantity",productId);
	}

	class ProductProfileImgElement {
		String productId;
		String fileName;
		String url;

		public ProductProfileImgElement(String productId, String fileName, String url) {
			this.productId = productId;
			this.fileName = fileName;
			this.url = url;
		}
	}

	class ProductDescriptionElement {
		String productId;
		String url;
		String productDescription;

		public ProductDescriptionElement(String productId, String url, String productDescription) {
			this.productId = productId;
			this.url = url;
			this.productDescription = productDescription;
		}
	}
}
