package io.github.im2back.transfermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TransferMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferMicroserviceApplication.class, args);
	}

}
