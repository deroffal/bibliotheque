package fr.deroffal.bibliotheque.webapp.login;

//@SpringBootTest
//@ExtendWith(ErsatzServerSupport.class)
class LoginServiceIT {
	//FIXME utiliser Wiremock
	//
	//	private final ErsatzServer ersatzServer = new ErsatzServer();
	//	@Autowired
	//	private LoginService loginService;
	//
	//	@Test
	//	@DisplayName("Recherche d'un utilisateur par son login")
	//	void loadUserByUsername() {
	//		ersatzServer.expectations(
	//				expectations -> expectations.get("/user/admin").called(1).responder(
	//						response -> response.code(SC_OK).body(readFile("/fr/deroffal/bibliotheque/webapp/login/userAdmin.json"), APPLICATION_JSON))
	//		).start();
	//		ReflectionTestUtils.setField(loginService, "authentificationUrl", ersatzServer.getHttpUrl());
	//
	//		final UserDetails userDetails = loginService.loadUserByUsername("admin");
	//
	//		assertEquals("admin", userDetails.getUsername());
	//		assertEquals("$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e", userDetails.getPassword());
	//		assertEquals(List.of("ROLE_ADMIN", "ROLE_USER"), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));
	//
	//		assertTrue(ersatzServer.verify());
	//	}
	//
	//	@Test
	//	@DisplayName("Recherche d'un utilisateur par son login - pas d'utilisateur trouvé")
	//	void loadUserByUsername_notFound() {
	//		ersatzServer.expectations(
	//				expectations -> expectations.get("/user/admin").called(1).responder(
	//						response -> response.code(SC_NOT_FOUND))
	//		).start();
	//		ReflectionTestUtils.setField(loginService, "authentificationUrl", ersatzServer.getHttpUrl());
	//
	//		assertThrows(UsernameNotFoundException.class, ()->loginService.loadUserByUsername("admin"));
	//
	//		assertTrue(ersatzServer.verify());
	//	}

}
