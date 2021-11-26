package fr.deroffal.bibliotheque.livre.adapter.apidocs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spring.web.plugins.Docket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors.joining

@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): Docket {
        val description = lireDescription("/apidocs/livre.md")
        val apiInfo = ApiInfoBuilder()
            .title("Bibliothèque - API Livre")
            .description(description)
            .build()
        return Docket(SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    private fun lireDescription(emplacement: String) = javaClass.getResourceAsStream(emplacement)
        ?.let {
            BufferedReader(InputStreamReader(it))
                .lines()
                .collect(joining(System.lineSeparator()))
        }

}
