package fr.deroffal.bibliotheque.livre.adapter.authentification

import io.vavr.control.Try
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class UserDetailsServiceImpl(
    private val userDetailsClient: UserDetailsClient,
    private val userDetailsMapper: UserDetailsMapper
) : UserDetailsService {

    override fun loadUserByUsername(username: String) =
        Try.of { userDetailsClient.loadUserByUsername(username) }
            .map { userDetailsMapper.toUserDetails(it) }
            .onFailure { println(it) }
            .getOrElseThrow(Supplier { UsernameNotFoundException("Utilisateur $username inconnu !") })

}
