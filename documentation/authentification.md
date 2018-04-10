# Authentification

## Mise en place

On va utiliser la dépendance de SpringBoot : 
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```
Une fois importée, elle va automatiquement déclencher un premier niveau de sécurité :
Au lancement du serveur, on va voir dans les logs qu'un mot de passe à été généré pour accéder à l'application (il est associer au login *user*).
```
Using generated security password: 34f93df6-375f-4eb3-96bc-c9e99203ced2
```
En appelant les services, on va alors devoir transmettre ce couple d'identifiant, ce qui n'est pas pratique puisqu'il va changer à chaque lancement du serveur. Pour pallier à cela, il est
 possible d'enregistrer un utilisateur *in-memory* par défaut en utilisant le fichier `application.properties` et en renseignant les deux proprietés suivantes : 
 ```
 spring.security.user.name=user # Default user name.
 spring.security.user.password= # Password for the default user name.
```

## Basic Auth avec UserDetails
L'idée est d'utiliser les utilisateurs stockés en base de données pour gérer l'accès aux services. Pour cela, on va d'abord faire implémenter le [service dédié aux utilisateur](../master/src/main/java/fr/deroffal/portail/user/service/UserService.java) 
l'interface `UserDetailsService`, qui possède une méthode ` UserDetails loadUserByUsername(final String login)` pour charger un utilisateur de la base de données.

On va créer une classe [SecurityConfiguration](../master/src/main/java/fr/deroffal/portail/SecurityConfiguration.java) qui sera chargé de la configuration de la sécurisation de notre application.
```
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
```
L'annotation `@EnableWebSecurity` indique qu'il s'agit de la classe de configuration dédiée à la sécurité de l'application.

On crée un Bean de type `PasswordEncoder` qui va être chargé de crypté les mots de passe qui viendront des utilisateurs qui créent leur compte, ainsi que de comparer des mots de passe 
soumis à ceux des utilisateurs retournés par notre UserDetailService.

La méthode `void configureGlobal(final AuthenticationManagerBuilder auth)` va nous permettre d'injecter à la configuration notre UserDetailsService que nous avons personnalisé pour qu'il 
puisse aller chercher nos utilisateurs. On fournit de plus le PasswordEncoder pour lui permettre de vérifier les mots de passe.