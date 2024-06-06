package io.github.im2back.transfermicroservice.infra.configurations;

import feign.codec.ErrorDecoder;
import io.github.im2back.transfermicroservice.exceptions.CustomErrorDecoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}