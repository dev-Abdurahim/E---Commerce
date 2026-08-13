package org.example.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API")
                        .version("1.0.0")
                        .description("Mini onlayn do'kon uchun REST API. " +
                                "Autentifikatsiya, mahsulot/kategoriya boshqaruvi, " +
                                "savatcha va buyurtma (checkout) funksiyalarini o'z ichiga oladi.")
                        .contact(new Contact()
                                .name("Abdurahim")
                                .email("abdurahimbohodirov25@gamil.com")
                                .url("https://github.com/dev-Abdurahim/E---Commerce"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))

                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT tokenni 'Bearer {token}' formatida emas, " +
                                        "faqat tokenning o'zini kiriting — 'Bearer ' qismini " +
                                        "Swagger UI o'zi avtomatik qo'shadi")))

                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
