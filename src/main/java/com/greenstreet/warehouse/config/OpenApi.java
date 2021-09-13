package com.greenstreet.warehouse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                        new Info()
                                .title("Flowers warehouse Api")
                                .version("1.0.0")
                );
    }
}
