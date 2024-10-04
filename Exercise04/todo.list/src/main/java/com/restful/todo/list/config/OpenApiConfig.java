package com.restful.todo.list.config;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("OpenApiConfig")
@Schema(description = "Configuração global do OpenAPI para a aplicação.")
public class OpenApiConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean("customOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para Gerenciamento de Todo List")
                        .description("Solutis School Dev Trail - Nivelamento - 2024")
                        .version(apiVersion)
                        .license(new License().url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact()
                                .name("Vinicius dos Santos Andrade")
                                .url("https://www.linkedin.com/in/viniciusdsandrade/")
                                .email("vinicius_andrade2010@hotmail.com"))
                );
    }

    @Bean("publicApi")
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}