package com.verx.miniautorizador.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("API Minipautorizador")
                        .description("Desafio técnico")
                        .version("1.0.0")
                        .license(new License().name("Licença")
                                .url("https://www.linkedin.com/in/inaelrodrigues/")));
    }
}
