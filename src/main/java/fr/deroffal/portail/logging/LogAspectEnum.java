package fr.deroffal.portail.logging;

/**
 * Permet d'ordonner les affichage des logs pour chaque aspect.
 */
public enum LogAspectEnum {

	LOG_EXECUTION_TIME(0), LOG_METHOD_NAME(1);

	private final int ordre;

	LogAspectEnum(final int ordre) {
		this.ordre = ordre;
	}

	public int getOrdre() {
		return ordre;
	}
}
