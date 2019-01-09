package com.company.program.resource.resource;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.company.program.api"})
@ComponentScan(basePackages = {"com.company.program"})
@EnableHystrix
public class ProgramServer {

	
	public static void main(String[] args) {
		SpringApplication.run(ProgramServer.class, args);

	}

}
