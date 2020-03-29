package com.yosep.product.controller;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.yosep.product.repository.ProductDAO;

@Component
@EnableBinding(ProductSink.class)
public class ProductReceiver {
	@Autowired
	ProductDAO productDAO;
	
	public ProductReceiver() {
		
	}
	
	@ServiceActivator(inputChannel = ProductSink.PRODUCTQ)
	public void accept(String productId) {
		System.out.println(productId);
		productDAO.setProductQuantity(productId);
	}
	
//	@Bean
//	Queue queue() {
//		System.out.println("ProductQ create!!!");
//		return new Queue("ProductQ", false);
//	}
//	
//	@RabbitListener(queues="ProductQ")
//	public void processMessage(String productId) {
//		System.out.println("구매 메시지 도착");
//		productDAO.setProductQuantity(productId);
//	}
}

interface ProductSink {
	public static String PRODUCTQ = "productQ";
	@Input("productQ")
	public MessageChannel productQ();
}
