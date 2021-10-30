package fr.deroffal.bibliotheque.commons.logging;

import org.springframework.core.Ordered;

abstract class AbstractLogingAspect implements Ordered {

	//On active l'aspect uniquement sur l'api d'authentification (partie utilisateur)
	static final String TARGET_BASE_PACKAGE = "execution(* fr.deroffal.bibliotheque.authentification.utilisateur..*(..))";

	abstract LogAspectOrder getLogAspectEnum();

	@Override
	public int getOrder() {
		return getLogAspectEnum().getOrdre();
	}

}
