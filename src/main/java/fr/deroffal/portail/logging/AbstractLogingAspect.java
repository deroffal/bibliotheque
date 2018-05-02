package fr.deroffal.portail.logging;

import org.springframework.core.Ordered;

abstract class AbstractLogingAspect implements Ordered {

	static final String TARGET_BASE_PACKAGE = "execution(* fr.deroffal.portail..*(..))";

	abstract LogAspectEnum getLogAspectEnum();

	@Override
	public int getOrder() {
		return getLogAspectEnum().getOrdre();
	}

}
