package com.project.steamtwitchintegration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    // localhost:8080/swagger-ui/index.html

    @Bean
    public OpenAPI defineOpenAPI(){
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Twitch Steam integration");

        Info info = new Info()
            .title("Steam Twitch integration API")
            .version("1.0")
            .description("This API provides data about correlation between popularity of games on Twitch and Steam");

        return new OpenAPI()
            .info(info)
            .servers(List.of(server));
    }

}
