package com.keyboardforces.ChatService;

import com.keyboardforces.ChatService.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.Resource;

@EnableDiscoveryClient
@SpringBootApplication
public class ChatServiceApplication implements CommandLineRunner {

	 @Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
