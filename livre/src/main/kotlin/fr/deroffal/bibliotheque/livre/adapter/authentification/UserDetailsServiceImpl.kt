package fr.deroffal.bibliotheque.livre.adapter.authentification

import io.vavr.control.Try
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class UserDetailsServiceImpl(
    private val userDetailsClient: UserDetailsClient,
    private val userDetailsMapper: UserDetailsMapper
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        Try.of { userDetailsClient.loadUserByUsername(username) }
            .map { userDetailsMapper.toUserDetails(it) }
            .getOrElseThrow(Supplier { UsernameNotFoundException(username) })

}
