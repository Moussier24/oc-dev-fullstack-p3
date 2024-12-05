package com.ocangjava.portail.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI portailOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Portail de Location")
                        .description("API pour la gestion des locations")
                        .version("1.0")
                        .contact(new Contact()
                                .name("OcangJava")
                                .email("contact@ocangjava.com")));
    }

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> {
            openApi.getComponents().getSchemas().values().forEach(schema -> {
                Map<String, Schema> properties = schema.getProperties();
                if (properties != null) {
                    var newProperties = new java.util.LinkedHashMap<String, Schema>();
                    properties.forEach((key, value) -> {
                        String newKey = camelToSnakeCase(key);
                        newProperties.put(newKey, value);
                    });
                    schema.setProperties(newProperties);
                }
            });
        };
    }

    private String camelToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}