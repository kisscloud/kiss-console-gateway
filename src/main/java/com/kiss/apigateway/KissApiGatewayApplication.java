package com.kiss.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class KissApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KissApiGatewayApplication.class, args);
    }
}
