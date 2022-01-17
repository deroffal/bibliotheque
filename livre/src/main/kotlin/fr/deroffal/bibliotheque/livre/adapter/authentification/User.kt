package fr.deroffal.bibliotheque.livre.adapter.authentification

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import fr.deroffal.bibliotheque.securite.details.JwtUserDetails
import org.springframework.stereotype.Component


@Component
class UserDetailsMapperImpl : UserDetailsMapper {
    override fun toUserDetails(user: User) = JwtUserDetails(user.username, user.password)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(val username: String, val password: String)

interface UserDetailsMapper {
    fun toUserDetails(user: User): JwtUserDetails
}
