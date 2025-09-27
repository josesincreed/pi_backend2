package com.example.pib2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//http://localhost:8080/swagger-ui.html para ver la documentación
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:PI Backend 2}")
    private String applicationName;

    @Value("${server.port:8080}")
    private String serverPort;


}