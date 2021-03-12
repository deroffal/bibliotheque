package fr.deroffal.bibliotheque.api.authentification;

import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Path : /swagger-ui.html
 */
//@formatter:off
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .groupName("all")
                .select()
                .apis(withClassAnnotation(Api.class))
                .build();
    }

    @Bean
    public Docket publicApi() {
        return new Docket(SWAGGER_2)
                .groupName("public")
                .select()
                .apis(any())
                .paths(s -> s.contains("/public/"))
                .build();
    }

}
