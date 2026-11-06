package com.example.TicketBooking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String BEARER_AUTH = "Bearer Authentication";

    @Bean
    public OpenAPI openAPI() {
        // One security scheme makes Swagger UI show the Authorize button.
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Booking API")
                        .description("Movie ticket booking API with Spring Boot, Spring Security, and JWT")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes(BEARER_AUTH, bearerScheme))
                // Swagger UI adds the Bearer prefix when sending an access token.
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
    }
}
