package fr.deroffal.bibliotheque.livre.configuration

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["fr.deroffal.bibliotheque"])
class FeignConfiguration
