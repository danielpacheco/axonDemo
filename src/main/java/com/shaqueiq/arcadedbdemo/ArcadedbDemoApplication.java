package com.shaqueiq.arcadedbdemo;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ArcadedbDemoApplication {

	public static void main(String[] args) {
//		Configurer configurer = DefaultConfigurer.defaultConfiguration();
		SpringApplication.run(ArcadedbDemoApplication.class, args);
		Configurer configurer = DefaultConfigurer.defaultConfiguration();
		System.out.println("***V2");
	}

}
