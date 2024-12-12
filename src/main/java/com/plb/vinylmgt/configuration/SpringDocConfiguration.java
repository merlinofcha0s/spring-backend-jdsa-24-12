package com.plb.vinylmgt.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI vinylOpenApiDoc() {
        return new OpenAPI()
                .info(new Info().title("Vinyl API")
                        .description("Vinyl Sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://vinyl-app-license.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("My Description")
                        .url("http://vinyl-wiki.org"));
    }

    @Bean
    public GroupedOpenApi publicAPI() {
        return GroupedOpenApi.builder()
                .group("vinyl-public")
                .pathsToMatch("/api/vinyls/**")
                .build();
    }

    @Bean
    public GroupedOpenApi privateAPI() {
        return GroupedOpenApi.builder()
                .group("user-private")
                .pathsToMatch("/api/users/**")
                .build();
    }

}
