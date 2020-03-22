package fr.deroffal.portail.api.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateUtils {

	private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("CET");

	private Clock clock = Clock.system(DEFAULT_ZONE_ID);

	public LocalDateTime newLocalDateTime() {
		return LocalDateTime.now(clock);
	}

	/*****************************************************************************************/

	/** A n'utiliser que dans les tests pour fixer date et heure. */
	public void fixTime(LocalDateTime date) {
		clock = Clock.fixed(date.atZone(DEFAULT_ZONE_ID).toInstant(), DEFAULT_ZONE_ID);
	}

	/** A n'utiliser que dans les tests pour remise à 0 de date et heure. */
	public void unfixTime() {
		clock = Clock.system(DEFAULT_ZONE_ID);
	}

}
