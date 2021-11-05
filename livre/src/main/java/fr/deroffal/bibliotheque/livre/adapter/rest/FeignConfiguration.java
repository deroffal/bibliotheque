package fr.deroffal.bibliotheque.livre.adapter.rest;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "fr.deroffal.bibliotheque")
public class FeignConfiguration {

}
