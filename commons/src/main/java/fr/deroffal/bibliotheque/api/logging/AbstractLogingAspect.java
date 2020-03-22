package fr.deroffal.bibliotheque.api.logging;

import org.springframework.core.Ordered;

abstract class AbstractLogingAspect implements Ordered {

	static final String TARGET_BASE_PACKAGE = "execution(* fr.deroffal.bibliotheque.api.authentification..*(..))";//FIXME il ne faut pas laisser logguer dans api.logging sous peine de régression, mais on peut être plus large (api.*)

	abstract LogAspectOrder getLogAspectEnum();

	@Override
	public int getOrder() {
		return getLogAspectEnum().getOrdre();
	}

}
