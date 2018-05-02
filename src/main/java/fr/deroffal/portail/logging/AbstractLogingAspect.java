package fr.deroffal.portail.logging;

import org.springframework.core.Ordered;

abstract class AbstractLogingAspect implements Ordered {

	/**
	 * TODO : pour l'instant une seule méthode, à généraliser.
	 */
	static final String TARGET = "execution(* fr.deroffal.portail.controller.PingController.*(..))";

	abstract LogAspectEnum getLogAspectEnum();

	@Override
	public int getOrder() {
		return getLogAspectEnum().getOrdre();
	}

}
