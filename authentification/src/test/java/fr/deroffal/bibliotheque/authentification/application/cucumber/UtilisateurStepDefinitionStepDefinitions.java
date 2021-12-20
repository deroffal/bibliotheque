package fr.deroffal.bibliotheque.authentification.application.cucumber;

import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.deroffal.bibliotheque.authentification.application.CreationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.authentification.application.UserNotFoundException;
import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.mockito.ArgumentMatcher;

@RequiredArgsConstructor
public class UtilisateurStepDefinitionStepDefinitions {

    private final CreationUtilisateurService creationUtilisateurService;
    private final RecuperationUtilisateurService recuperationUtilisateurService;

    private final UserRepositoryAdapter userRepositoryAdapter;

    private final Map<String, String> correspondanceMotDePasse = new HashMap<>();

    private Utilisateur utilisateurACree;

    private Utilisateur utilisateurCree;
    private UserAlreadyExistsException userAlreadyExistsException;

    private String nomUtilisateurRecherche;
    private Utilisateur utilisateurTrouve;
    private UserNotFoundException userNotFoundException;

    @Given("les mots de passes avec leur mot de passe encodé correspondant :")
    public void initialiserCorrespondanceMotDePasse(final List<Map<String, String>> datas) {
        datas.forEach(data -> correspondanceMotDePasse.put(data.get("motDePasse"), data.get("motDePasseEncode")));
    }

    @Given("les utilisateurs existants :")
    public void initialiserUtilisateurs(final List<Utilisateur> utilisateurs) {
        final Map<String, Utilisateur> utilisateurParNom = utilisateurs.stream().collect(Collectors.toMap(Utilisateur::username, identity()));

        final Collection<String> nomUtilisateursExistants = utilisateurParNom.keySet();
        when(userRepositoryAdapter.existsByUsername(anyString())).thenAnswer(invocationOnMock -> {
            final String nom = invocationOnMock.getArgument(0);
            return nomUtilisateursExistants.contains(nom);
        });

        when(userRepositoryAdapter.findByUsername(anyString())).thenAnswer(invocationOnMock -> {
            final String nom = invocationOnMock.getArgument(0);
            return Optional.ofNullable(utilisateurParNom.get(nom));
        });
    }

    @Given("l'utilisateur suivant à créer :")
    public void initialiserMockCreationUtilisateur(final Utilisateur utilisateur) {
        utilisateurACree = utilisateur;
        final Utilisateur utilisateurRetourne = new Utilisateur(null, utilisateur.username(), correspondanceMotDePasse.get(utilisateur.password()),
            emptyList());
        when(userRepositoryAdapter.create(argThat(userMatches(utilisateur)))).thenReturn(utilisateurRetourne);
    }

    @Given("le nom d'utilisateur {string} à chercher")
    public void initialiserParametreRechercheUtilisateur(final String nomUtilisateurRecherche) {
        this.nomUtilisateurRecherche = nomUtilisateurRecherche;
    }

    @When("^je demande la création de l'utilisateur$")
    public void creerUtilisateur() {
        try {
            utilisateurCree = creationUtilisateurService.create(utilisateurACree);
        } catch (final UserAlreadyExistsException e) {
            userAlreadyExistsException = e;
        }
    }

    @When("j'appelle le service de recherche d'utilisateur")
    public void jAppelleLeServiceDeRechercheDUtilisateur() {
        try {
            utilisateurTrouve = recuperationUtilisateurService.getByLogin(nomUtilisateurRecherche);
        } catch (final UserNotFoundException e) {
            userNotFoundException = e;
        }
    }

    @Then("l'utilisateur suivant est créé :")
    public void verificationCreationUtilisateur(final Utilisateur utilisateur) {
        assertThat(utilisateurCree).isNotNull();
        assertThat(utilisateur.username()).isEqualTo(utilisateurCree.username());
        assertThat(utilisateur.password()).isEqualTo(utilisateurCree.password());
    }

    @Then("j'obtiens une erreur comme quoi l'utilisateur {string} existe déjà")
    public void verifierErreurUtilisateurDejaExistant(final String username) {
        assertThat(userAlreadyExistsException).isNotNull();
        assertThat(userAlreadyExistsException.getLogin()).isEqualTo(username);
    }

    @Then("j'obtiens l'utilisateur suivant :")
    public void verificationRecuperationUtilisateur(final Utilisateur utilisateur) {
        assertThat(utilisateurTrouve).isNotNull();
        assertThat(utilisateur.username()).isEqualTo(utilisateurTrouve.username());
        assertThat(utilisateur.password()).isEqualTo(utilisateurTrouve.password());
    }

    @Then("j'obtiens une erreur comme quoi l'utilisateur {string} n'existe pas")
    public void verifierErreurUtilisateurInconnu(final String username) {
        assertThat(userNotFoundException).isNotNull();
        assertThat(userNotFoundException.getLogin()).isEqualTo(username);
    }

    @DataTableType
    public Utilisateur userDto(final Map<String, String> datas) {
        final String nom = datas.get("nom");
        final String motDePasse = datas.get("motDePasse");
        return new Utilisateur(null, nom, motDePasse, emptyList());
    }

    private static ArgumentMatcher<Utilisateur> userMatches(final Utilisateur utilisateur) {
        return user -> user.username().equals(utilisateur.username()) && user.password().equals(utilisateur.password());
    }
}
