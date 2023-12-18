package com.modooFoods.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation",
                description = "API Documents 입니다.",
                version = "v1.0.0"
        )
)
public class Swagger2Config {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("Bearer");

        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("Jwt token"))
                .getComponents().addSecuritySchemes("Jwt token", securityScheme);
    }
}