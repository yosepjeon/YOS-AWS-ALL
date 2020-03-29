package com.yosep.order.controller;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RefreshScope
@EnableBinding(ProductSource.class)
@Component
public class ProductSender {
	public ProductSender() {
		
	}
	
	@Output(ProductSource.PRODUCTQ)
	@Autowired
	private MessageChannel messageChannel;
	
	public void send(String productId) {
		messageChannel.send(MessageBuilder.withPayload(productId).build());
	}
}

interface ProductSource {
	public static String PRODUCTQ = "productQ";
	@Output("productQ")
	public MessageChannel productQ();
}

//@Component
//@Lazy
//public class ProductSender {
//	RabbitMessagingTemplate template;
//	
//	@Autowired
//	public ProductSender(RabbitMessagingTemplate template) {
//		// TODO Auto-generated constructor stub
//		this.template = template;
//	}
//	
//	@Bean
//	Queue queue() {
//		return new Queue("ProductQ", false);
//	}
//	
//	public void send(String productId) {
//		System.out.println("send productId!!!");
//		template.convertAndSend("productId",productId);
//	}
//}
