package fr.deroffal.bibliotheque.securite.details

import spock.lang.Specification

class JwtUserDetailsSpec extends Specification {

    def "POJO test : JwtUserDetails"() {
        when:
        JwtUserDetails user = new JwtUserDetails('Jean', 'Azerty12*')

        then:
        user.username == 'Jean'
        user.password == 'Azerty12*'
        user.authorities == []
        user.accountNonExpired
        user.accountNonLocked
        user.credentialsNonExpired
        user.enabled
    }
}
