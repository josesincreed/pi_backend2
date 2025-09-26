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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de Desarrollo Local"),
                        new Server()
                                .url("https://api.ejemplo.com")
                                .description("Servidor de Producción")
                ))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Autenticación HTTP Basic. Usar credenciales: admin/admin123 o user/user123")
                        )
                );
    }

    private Info apiInfo() {
        return new Info()
                .title(applicationName + " - API REST")
                .description(
                        "API REST para gestión de usuarios, items y préstamos con Spring Boot.\n\n" +
                                "**Características principales:**\n" +
                                "- Autenticación con Spring Security (Basic Auth)\n" +
                                "- Control de acceso basado en roles (ADMIN/USER)\n" +
                                "- Validación de datos con Bean Validation\n" +
                                "- Persistencia con JPA/Hibernate\n" +
                                "- Monitoreo con Spring Boot Actuator\n\n" +
                                "**Credenciales de prueba:**\n" +
                                "- Admin: `admin` / `admin123`\n" +
                                "- Usuario: `user` / `user123`\n" +
                                "- Usuario: `john` / `john123`\n\n" +
                                "**Endpoints públicos:**\n" +
                                "- `/actuator/health` - Estado de la aplicación\n" +
                                "- `/actuator/info` - Información de la aplicación\n" +
                                "- `/swagger-ui.html` - Documentación interactiva\n" +
                                "- `/v3/api-docs` - Especificación OpenAPI JSON"
                )
                .version("1.0.0")
                .contact(new Contact()
                        .name("Equipo de Desarrollo")
                        .email("desarrollo@ejemplo.com")
                        .url("https://github.com/ejemplo/pi-backend2")
                )
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                );
    }
}