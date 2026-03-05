package com.hotelms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(
    servers = {@Server(url = "${swagger-server}")},
    info =
        @Info(title = "Hotel MS API", version = "1.0", description = "Hotel Management System API"))
public class HotelMSApplication {
  public static void main(String[] args) {
    SpringApplication.run(HotelMSApplication.class, args);
  }
}
