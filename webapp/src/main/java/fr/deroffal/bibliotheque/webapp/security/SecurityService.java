package fr.deroffal.bibliotheque.webapp.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	public boolean isUserLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return !(auth instanceof AnonymousAuthenticationToken);
	}
}
