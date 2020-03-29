package com.yosep.order.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yosep.order.entity.Order;

@Repository
public class OrderDAOImpl implements OrderDAO{
	static private final String NAMESPACE = "com.yosep.order.mapper.orderMapper";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Order> orderListBeforeBuy(String userId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".getOrderListBeforeBuy",userId);
	}
	
	@Override
	public List<Order> orderListAfterBuy(String userId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".getOrderListAfterBuy",userId);
	}

	@Override
	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".createOrder",order);
	}

	@Override
	public void updateOrder(String userId) {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".buyUpdateOrder",userId);
	}

	@Override
	public void setOrderAddressElements(Order order) {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".updateOrdersForPay",order);
	}
	
}
