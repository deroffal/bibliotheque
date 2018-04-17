package fr.deroffal.portail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@formatter:off
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * Les pattern d'URL autorisée sans authentification.
	 * <li>
	 *     <ul>url commencant par public</ul>
	 *     <ul>swagger-ui</ul>
	 * </li>
	 */
	private static final String[] AUTH_WHITELIST = {
			"/public/**",
			"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"
	};

	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers("/**/*").denyAll();
	}
}