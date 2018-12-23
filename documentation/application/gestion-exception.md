# Gestion des exceptions
Pour ne pas avoir une exception toute moche lorsqu'un nullPointer survient, ou bien pour ne pas avoir à traiter des cas d'erreur fonctionnelle dans le code du contrôleur, il est possible 
d'utiliser un RestControllerAdvice. Cette classe ([PortailRestControllerAdvice.java](../../src/main/java/fr/deroffal/portail/exception/PortailRestControllerAdvice.java)) va
intercepter les exceptions lancées par le contrôleur REST et, si on lui a indiqué comment la traiter, va retourner la réponse attendue.

Notre classe est annotée par `@RestControllerAdvice`. Elle comportera des méthodes annotées par `@ExceptionHandler`, avec pour attribut le type d'exception que'elles seront chargées de 
gérer. La méthode va pouvoir récupérer, en plus de l'exception, la requête HTTP qui en était la cause et pourra retourner une `ResponseEntity` adaptée à la requête et à l'erreur.