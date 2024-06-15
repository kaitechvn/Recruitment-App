package com.example.recruitment.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info
                   (contact = @Contact(name = "Khai Bui",
                                       email = "quockhaihn@gmail.com",
                                       url = "http://kaitech.dev.vn"),
                   version = "1.0.0",
                   title = "API doc for recruitment project"),
                   servers = {@Server(url = "http://localhost:8080", description = "LOCAL ENV"),
                              @Server(url = "http://localhost:8083", description = "PROD ENV")}
                   )

@SecurityScheme(
                name = "Authorization",
                description = "JWT Auth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER)
@Configuration
public class OpenApiConfig {}

