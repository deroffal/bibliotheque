package fr.deroffal.portail.webapp.login;

import fr.deroffal.portail.webapp.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@Autowired
	private SecurityService securityService;

	@GetMapping("/login")
	public String login() {
		return securityService.isUserLoggedIn() ? index() : "login.html";
	}

	@PostMapping("/index")
	public String index() {
		return "index.html";
	}

}