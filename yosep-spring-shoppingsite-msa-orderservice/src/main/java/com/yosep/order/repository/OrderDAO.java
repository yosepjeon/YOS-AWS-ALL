package com.yosep.order.repository;

import java.util.List;

import com.yosep.order.entity.Order;

public interface OrderDAO {
	// 01. 주문 전 목록
	public List<Order> orderListBeforeBuy(String userId);
	
	// 주문 완료 목록
	public List<Order> orderListAfterBuy(String userId);
	
	// 02. 주문 생성
	public void createOrder(Order order);
	
	// 03. 주문 업데이트
	public void updateOrder(String userId);
	
	// 04. 주문 주소값 넣기
	public void setOrderAddressElements(Order order);
}
