package com.shenll.shelogisticsagentservice.configurations;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SwaggerConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Shelogistics")
                        .description("<span style=\"color:red; font-size:14px;\"><b>Agent Services</b></span>")
                        .version("v3")
                        .license(new License().name("2024").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Shenll Techonology Solutions")
                        .url("https://www.shenll.com/"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
