package fr.deroffal.portail.api.authentification.utilisateur;

import fr.deroffal.portail.api.authentification.role.RoleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityTest {

	@Test
	@DisplayName("Récupération des rôles sous String[] pour utilisation par SpringSecurity")
	void getRolesAsStrings() {
		final UserEntity user = new UserEntity();
		final RoleEntity roleAdmin = new RoleEntity("ADMIN");
		final RoleEntity roleUser = new RoleEntity("USER");
		user.setRoles(List.of(roleAdmin, roleUser));

		final String[] rolesAsStrings = user.getRolesAsStrings();
		assertAll(
				() -> assertEquals(2, rolesAsStrings.length),
				() -> assertEquals("ADMIN", rolesAsStrings[0]),
				() -> assertEquals("USER", rolesAsStrings[1])
		);
	}
}