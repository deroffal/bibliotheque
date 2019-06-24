package fr.deroffal.portail.api.logging;

/**
 * Permet d'ordonner les affichage des logs pour chaque aspect.
 */
public enum LogAspectOrder {

	LOG_EXECUTION_TIME(0), LOG_METHOD_NAME(1);

	private final int ordre;

	LogAspectOrder(final int ordre) {
		this.ordre = ordre;
	}

	public int getOrdre() {
		return ordre;
	}
}