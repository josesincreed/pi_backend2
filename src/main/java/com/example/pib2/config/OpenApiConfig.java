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

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class OpenApiConfig {

    @Value("El Arte de Vivir")
    private String applicationName;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Servidores donde corre la API
                .servers(List.of(
                        new Server().url("http://localhost:" + serverPort).description("Servidor Local"),
                        new Server().url("https://api.mi-dominio.com").description("Servidor Producción")
                ))
                // Info general de la API
                .info(new Info()
                        .title(applicationName + " - API Rest Documentation")
                        .version("1.0.0")
                        .description("Documentación de la API del proyecto **" + applicationName + "**. \n\n" +
                                "Incluye gestión de usuarios, productos, categorías y compras.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo PHOENIX RISE")
                                .email("josedavidparrauribe@gmail.com")
                                .url("https://github.com/josesincreed/pi_backend2"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                // 🔑 Configuración de seguridad GLOBAL
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Autenticación básica con usuario y contraseña")));
    }
}
