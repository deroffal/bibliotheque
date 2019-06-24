package fr.deroffal.portail.webapp.login;

import fr.deroffal.portail.webapp.http.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

	@Value("${portail.authentification.url}")
	private String authentificationUrl;

	@Autowired
	private HttpService httpService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final String url = authentificationUrl + "/user/" + username;
		final UserResponse response = httpService.get(
				url,
				UserResponse.class,
				() -> { throw new UsernameNotFoundException("Inconnu!"); }
				);
		return User.withUsername(response.getLogin()).password(response.getPassword()).roles(response.getRoles()).build();
	}
}
