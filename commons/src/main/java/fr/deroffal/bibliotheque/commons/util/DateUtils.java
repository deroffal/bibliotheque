package fr.deroffal.bibliotheque.commons.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateUtils {

	/**Default zone : "Europe/Paris".*/
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("CET");

    private Clock clock = Clock.system(DEFAULT_ZONE_ID);

    /**Wrapper for the current Instant (now).*/
    public Instant now() {
        return Instant.now(clock);
    }

    /**The LocalDateTime corresponding to the current Instant on the default timezone.*/
    public LocalDateTime newLocalDateTime() {
        return now().atZone(DEFAULT_ZONE_ID).toLocalDateTime();
    }

    /*****************************************************************************************/

    /**
     * A n'utiliser que dans les tests pour fixer date et heure.
     */
    public void fixTime(final LocalDateTime date) {
        clock = Clock.fixed(date.atZone(DEFAULT_ZONE_ID).toInstant(), DEFAULT_ZONE_ID);
    }

    /**
     * A n'utiliser que dans les tests pour remise à 0 de date et heure.
     */
    public void unfixTime() {
        clock = Clock.system(DEFAULT_ZONE_ID);
    }

}
