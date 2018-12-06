package com.kiss.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
public class KissApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KissApiGatewayApplication.class, args);
    }
}
