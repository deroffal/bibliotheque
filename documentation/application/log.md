# Logging
## Logging par aspect.
Deux exemples d'aspect pour le logging : [LogExecutionTimeAspect.java](../../src/main/java/fr/deroffal/portail/logging/LogExecutionTimeAspect.java) pour logger la durée
d'exécution d'une méthode, et [LogMethodAndArgumentAspect.java](../../src/main/java/fr/deroffal/portail/logging/LogMethodAndArgumentAspect.java) pour logger les appels et les
retours des méthodes.

La classe de l'aspect doit être annotée par les annotations `@Aspect` et `@Component` pour être géré comme aspect par Spring. Le fait d'implémenter l'interface `Ordered` permet à Spring 
de savoir dans quel ordre les aspects seront exécutés.

La méthode qui va être exécutée est annotée par `@Around` pour signifier que l'aspect va entourer l'appel de la méthode. On aurait pu utiliser les annotations `@Before` ou `@After` pour 
exécuter l'aspect respectivement avant ou après la méthode appelée. On lui passe en paramètre une expression régulière pour déterminer quelles méthodes seront impactées : ici,  `execution
(* fr.deroffal.portail..*(..))` signifie que l'on souhaite travailler sur toutes les méthodes public des classes dans tous les packages de l'application. Il est aussi possible par exemple
 de créer une annotation et de jouer l'aspect seulement sur les méthodes/classes annotées par celle-ci.
 
 La méthode de l'aspect possède un `JoinPoint` en paramètre. Il s'agit d'un événement portant la méthode à exécuter. A partir de celui-ci, on peut récupérer les informations sur la 
 méthode (signature, arguments passés, ...) ou bien encore invoquer l'exécution de la méthode.