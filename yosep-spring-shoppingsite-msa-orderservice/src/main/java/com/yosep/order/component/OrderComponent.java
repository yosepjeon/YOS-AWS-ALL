package com.yosep.order.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yosep.order.controller.ProductSender;
import com.yosep.order.entity.Order;
import com.yosep.order.repository.OrderDAO;

@RefreshScope
@Component
public class OrderComponent {
	
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	ProductSender productSender;
	
	public List<Order> orderListBeforeBuy(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.orderListBeforeBuy(userId);
	}
	
	public List<Order> orderListAfterBuy(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.orderListAfterBuy(userId);
	}

	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		orderDAO.createOrder(order);
	}

	@Transactional
	public void updateOrder(String userId) {
		// TODO Auto-generated method stub
		List<Order> orders = orderDAO.orderListBeforeBuy(userId);
		
		for(Order order : orders ) {
			orderDAO.updateOrder(order.getSenderId());
			productSender.send(order.getProductId());
		}
	}
	
	public void setOrderAddressElements(Order order) {
		orderDAO.setOrderAddressElements(order);
	}
}
