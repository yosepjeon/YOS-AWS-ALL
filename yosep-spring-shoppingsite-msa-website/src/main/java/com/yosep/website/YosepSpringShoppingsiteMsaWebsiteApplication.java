package com.yosep.website;

import javax.servlet.Filter;
import javax.servlet.http.HttpSessionListener;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.amqp.core.Queue;

@SpringBootApplication
@EnableDiscoveryClient
//public class YosepSpringShoppingsiteMsaWebsiteApplication implements CommandLineRunner {
public class YosepSpringShoppingsiteMsaWebsiteApplication {
	@Value("${application.service.name}")
	private static String serviceName;
	@Value("${file.upload-dir}")
	private static String root;

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" + "classpath:bootstrap.properties,"
			+ "classpath:aws.properties";

	public static void main(String[] args) {
//		SpringApplication.run(YosepSpringShoppingsiteMsaWebsiteApplication.class, args);
		new SpringApplicationBuilder(YosepSpringShoppingsiteMsaWebsiteApplication.class)
				.properties(APPLICATION_LOCATIONS).run(args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println("root!!:" + root);
//		System.out.println(serviceName);
//	}

	@Bean
	public HttpSessionListener httpSessionListener() {

		return new SessionListener();

	}
	
	@Bean
	public FilterRegistrationBean<Filter> encodingFilterBean() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.setFilter(characterEncodingFilter);
		return registrationBean;
	}

}

@Component
@Lazy
class OrderSender {
	RabbitMessagingTemplate template;

	@Autowired
	OrderSender(RabbitMessagingTemplate template) {
		this.template = template;
	}

	@Bean
	Queue queue() {
		return new Queue("OrderQ", false);
	}

	public void send(String userId) {
		template.convertAndSend(userId);
	}
}

@Configuration
class Config {
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@Configuration
class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/Users/jeon-yoseb/documents/yosep-spring-shoppingsite-msa-product2/**")
				.addResourceLocations("file:/Users/jeon-yoseb/documents/yosep-spring-shoppingsite-msa-product2/");

	}

}
