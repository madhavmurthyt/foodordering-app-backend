package com.upgrad.FoodOrderingApp.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI foodOrderingAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Ordering App API")
                        .version("1.0.0")
                        .description("REST API for the Food Ordering Application"));
    }
}
