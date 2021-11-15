package fr.deroffal.bibliotheque.livre.adapter.authentification

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "user", url = "\${bibliotheque.authentification.url}")
interface UserDetailsClient {

    @GetMapping("/user/{username}")
    fun loadUserByUsername(@PathVariable username: String): User
}
