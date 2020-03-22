package fr.deroffal.bibliotheque.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilsTest {

	private DateUtils dateUtils = new DateUtils();

	@Test
	@DisplayName("Retourner la LocalDateTime à l'heure")
	void newLocalDateTime() {
		final LocalDateTime localDateTime = LocalDateTime.parse("2019-05-05T13:35:23");
		dateUtils.fixTime(localDateTime);

		assertEquals(localDateTime, dateUtils.newLocalDateTime());

		dateUtils.unfixTime();
		assertTrue(dateUtils.newLocalDateTime().isAfter(localDateTime));
	}
}