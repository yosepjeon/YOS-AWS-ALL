package com.yosep.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class YosepSpringShoppingsiteMsaUserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(YosepSpringShoppingsiteMsaUserserviceApplication.class, args);
	}

}
