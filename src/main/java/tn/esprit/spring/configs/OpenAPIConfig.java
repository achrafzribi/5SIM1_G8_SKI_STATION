package tn.esprit.spring.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());

    }

    public Info infoAPI() {
        return new Info().title("\uD83C\uDFBF SKI STATION MANAGEMENT \uD83D\uDEA0")
                .description("Case Study - SKI STATION")
                .contact(contactAPI());
    }

    public Contact contactAPI() {
        Contact contact = new Contact().name("Mohamed Loukil")
                .email("loukil.mohamed@esprit.tn")
                .url("https://www.linkedin.com/in/mohamed-loukil/");
        return contact;
    }

    @Bean
    public GroupedOpenApi blocPublicApi() {
        return GroupedOpenApi.builder()
                .group("Test Management API")
                .pathsToMatch("/**/**")
                .pathsToExclude("**")
                .build();

    }


}