package com.yosep.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class YosepSpringShoppingsiteMsaOrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(YosepSpringShoppingsiteMsaOrderserviceApplication.class, args);
	}
}

//@Component
//class OrderReceiver {
//	@Autowired
//	ProductSender sender;
//	
//	@RabbitListener(queues="OrderQ")
//	public void processMessage(String userId) {
//		
//	}
//}
