package fr.deroffal.bibliotheque.securite

import fr.deroffal.bibliotheque.securite.filter.JwtAuthenticationEntryPoint
import fr.deroffal.bibliotheque.securite.filter.JwtRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
    private final UserDetailsService userDetailsService
    private final JwtRequestFilter jwtRequestFilter
    private final SecuriteConfig securiteConfig

    SecurityConfiguration(final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, final UserDetailsService userDetailsService, final JwtRequestFilter jwtRequestFilter, final SecuriteConfig securiteConfig) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint
        this.userDetailsService = userDetailsService
        this.jwtRequestFilter = jwtRequestFilter
        this.securiteConfig = securiteConfig
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder()
    }

    @Bean
    @Override
    AuthenticationManager authenticationManagerBean() throws Exception {
        super.authenticationManagerBean()
    }

    @Autowired
    void configureGlobal(final AuthenticationManagerBuilder auth, final PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(listeBlanche).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .csrf().disable()
    }

    private String[] getListeBlanche() {
        return securiteConfig.listeBlanche.toArray()
    }
}
