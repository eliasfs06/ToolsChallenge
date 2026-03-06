package com.api.pagamentos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração central da documentação OpenAPI (Swagger) da aplicação.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Cria a especificação OpenAPI com os metadados básicos do projeto.
     *
     * @return especificação OpenAPI.
     */
    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "basicAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("API Pagamentos")
                        .version("1.0.0")
                        .description("API REST para pagamento, estorno e consulta de transações.")
                        .license(new License().name("Uso interno")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")));
    }
}