package com.qifanfeng.matchengine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KOL Match Engine API")
                        .version("1.0.0")
                        .description("High-concurrency KOL matchmaking engine with cosine-similarity scoring, Redis caching, and RabbitMQ async processing.")
                        .contact(new Contact()
                                .name("Qifan Feng")
                                .url("https://github.com/KhalDragonFeng")
                                .email("contact@qifanfeng.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
