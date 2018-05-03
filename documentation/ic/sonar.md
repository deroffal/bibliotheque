# Sonar
## Configuration du Sonar
Utilisation de [SonarCloud]()https://sonarcloud.io/organizations/deroffal-github/projects). Je travaille dans une organisation : "deroffal-github"

## Lancement de l'analyse via Maven
Build maven pour exécuter l'analyse Sonar :

```
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.login= azd4534g1vs35r4d \
    -Dsonar.organization=deroffal-github
```
 * L'argument `-Dsonar.login` est un token d'accès, à configurer dans Sonar : My account -> Security -> Token.
 * L'argument `-Dsonar.organization` est spécifique à *SonarCloud*, on ne le retrouve pas pour une analyse sur un serveur local.
 
### jacoco-maven-plugin:prepare-agent   
Le plugin jacoco-maven-plugin:prepare-agent est utilisé pour la couverture de code des tests unitaires lors de l'analyse sonar. 
Un agent est un jar qui va jouer le rôle de "plugin" pour la JVM et qui peut intercepter le chargement d'une classe et modifier son bytecode dans le but de l'instrumenter. Étant donné qu'il est possible que le bytecode soit modifié par l'agent, il est préférable de ne pas envoyer *ce* .jar créé dans une release.

* [Exemple d'utilisation d'agent](http://blog.xebia.fr/2008/05/02/java-agent-instrumentez-vos-classes/) 

### sonar:sonar
Le plugin sonar:sonar lance l'analyse. Pour ne pas répéter les arguments dans le lancement du build, on peut les paramétrer via l'administration de Jenkins [voir paragraphe](#installation-et-configuration-dans-jenkins)

## Installation et configuration dans Jenkins
Dans l'administration de Jenkins, aller dans *Configure system* puis dans la rubrique *SonarQube servers* et renseigner les paramètres suivants :
 * **Name :** SonarDeroffal (Il s'agit du nom qui sera utilisé pour l'appeler depuis les builds)
 * **Server URL :** https://sonarcloud.io
 * **Server authentication token :** Le token d'authentification généré dans SonarQube
 * **Additional analysis properties :** Les arguments à passer dans le scanner de Sonar. Typiquement, on peut lui passer le token : `sonar.login=$SONAR_AUTH_TOKEN`. Malheureusement, ça n'a pas l'air de marcher pour la proprieté sonar.organization :disappointed:
