package fr.deroffal.bibliotheque.livre.adapter.apidocs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
}
