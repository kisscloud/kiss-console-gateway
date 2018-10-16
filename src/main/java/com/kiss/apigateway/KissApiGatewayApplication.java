package com.kiss.apigateway;

import com.kiss.apigateway.filter.PermissionControlFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

//@SpringBootApplication
@SpringCloudApplication
@EnableZuulProxy
public class KissApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KissApiGatewayApplication.class, args);
    }
}
