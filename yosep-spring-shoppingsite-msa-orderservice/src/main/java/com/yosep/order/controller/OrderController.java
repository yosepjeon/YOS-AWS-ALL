package com.yosep.order.controller;

import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yosep.order.component.OrderComponent;
import com.yosep.order.entity.Order;

@RestController
@CrossOrigin
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private OrderComponent orderComponent;
	
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	public int createOrder(@RequestBody Order order) {
		orderComponent.createOrder(order);
		
		return 200;
	}
	
	@RequestMapping(value="/updateOrder",method=RequestMethod.POST)
	public int updateOrder(@RequestParam("userId")String userId) {
		
		orderComponent.updateOrder(userId);
		
		return 200;
	}
	
	@RequestMapping(value="getUsersOrdersBeforeBuy")
	public List<Order> getOrdersByUserBeforeBut(String userId) {
		List<Order> orderList = orderComponent.orderListBeforeBuy(userId);
		
		return orderList;
	}
	
	@RequestMapping(value="getUsersOrdersAfterBuy")
	public List<Order> getOrdersByUserAfterBuy(String userId) {
		List<Order> orderList = orderComponent.orderListAfterBuy(userId);
		
		return orderList;
	}
	
	@RequestMapping(value="setOrderElements", method=RequestMethod.POST)
	public int setOrderElements(@RequestBody Order order) {
		orderComponent.setOrderAddressElements(order);
		
		return 200;
	}
}
