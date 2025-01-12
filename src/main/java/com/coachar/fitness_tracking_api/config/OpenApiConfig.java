package com.coachar.fitness_tracking_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Fitness Tracking API",
        version = "1.0",
        description = "API for managing fitness tracking activities, user profiles, and activity logs.",
        contact = @Contact(
            name = "Omkar",
            email = "omkar@mail.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Server"
        ),
        @Server(
            url = "https://localhost:8080",
            description = "Production Server"
        )
    }
)
public class OpenApiConfig {
}

