package fr.deroffal.bibliotheque.commons.logging;

import fr.deroffal.bibliotheque.commons.util.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.ZoneId;

import static fr.deroffal.bibliotheque.commons.logging.LogAspectOrder.LOG_EXECUTION_TIME;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogExecutionTimeAspectTest {

	@InjectMocks
	private LogExecutionTimeAspect logExecutionTimeAspect;

	@Mock
	private AppLoggerFactory appLoggerFactory;

	@Mock
	private DateUtils dateUtils;

	@Test
	@DisplayName("Logguer le temps d'exécution d'une méthode")
	void logExecutionTime() throws Throwable {
		final ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		final MonServiceImpl clazz = new MonServiceImpl();
		when(pjp.getTarget()).thenReturn(clazz);
		final Logger logger = mock(Logger.class);
		when(appLoggerFactory.getLogger(clazz.getClass())).thenReturn(logger);

		Signature signature = mock(Signature.class);
		when(pjp.getSignature()).thenReturn(signature);
		when(signature.getName()).thenReturn("nom méthode");

		final long start = 1558955926019L;
		final long stop = 1558955927231L;
		final long expectedDuration = stop - start;
		when(dateUtils.newLocalDateTime()).thenReturn(
				Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault()).toLocalDateTime(),
				Instant.ofEpochMilli(stop).atZone(ZoneId.systemDefault()).toLocalDateTime()
		);

		final Object expectedReturnedValue = new Object();
		when(pjp.proceed()).thenReturn(expectedReturnedValue);

		final Object o = logExecutionTimeAspect.logExecutionTime(pjp);

		Assertions.assertEquals(o, expectedReturnedValue);
		verify(logger).debug("Le temps d'exécution de la méthode {} est de {} ms", "nom méthode", expectedDuration);
	}

	@Test
	@DisplayName("L'ordre de l'aspect est LOG_EXECUTION_TIME")
	void getLogAspectEnum(){
		Assertions.assertEquals(LOG_EXECUTION_TIME, logExecutionTimeAspect.getLogAspectEnum());
	}

	private class MonServiceImpl {

	}
}
