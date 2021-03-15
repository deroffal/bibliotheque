package fr.deroffal.bibliotheque.webapp.login;

import fr.deroffal.bibliotheque.webapp.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final SecurityService securityService;

	@GetMapping("/login")
	public String login() {
		return securityService.isUserLoggedIn() ? index() : "login.html";
	}

	@PostMapping("/index")
	public String index() {
		return "index.html";
	}

}
