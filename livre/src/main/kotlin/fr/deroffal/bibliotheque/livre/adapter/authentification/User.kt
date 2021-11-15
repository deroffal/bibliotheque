package fr.deroffal.bibliotheque.livre.adapter.authentification

import fr.deroffal.bibliotheque.securite.details.JwtUserDetails
import org.springframework.stereotype.Component

data class User(val username: String, val password: String)

interface UserDetailsMapper {
    fun toUserDetails(user: User): JwtUserDetails
}

@Component
class UserDetailsMapperImpl : UserDetailsMapper {
    override fun toUserDetails(user: User) = JwtUserDetails(user.username, user.password)
}
