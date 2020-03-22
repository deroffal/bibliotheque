package fr.deroffal.bibliotheque.api.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static fr.deroffal.bibliotheque.api.logging.LogAspectOrder.LOG_METHOD_NAME;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogMethodAndArgumentAspectTest {

	@InjectMocks
	private LogMethodAndArgumentAspect logMethodAndArgumentAspect;

	@Mock
	private AppLoggerFactory appLoggerFactory;

	@Test
	@DisplayName("Si le logger n'est pas en mode debug, on ne fait rien")
	void logMethodAndArgument_whenLoggerDisabled() throws Throwable {
		final ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		final MonServiceImpl clazz = new MonServiceImpl();
		when(pjp.getTarget()).thenReturn(clazz);
		final Logger logger = mock(Logger.class);
		when(appLoggerFactory.getLogger(clazz.getClass())).thenReturn(logger);
		when(logger.isDebugEnabled()).thenReturn(false);

		final Object expectedReturnedValue = new Object();
		when(pjp.proceed()).thenReturn(expectedReturnedValue);

		Assertions.assertEquals(expectedReturnedValue, logMethodAndArgumentAspect.logMethodAndArgument(pjp));

		verify(pjp).getTarget();
		verify(pjp).proceed();
		verifyNoMoreInteractions(pjp);
	}

	@Test
	@DisplayName("Logguer le nom et les paramètres d'une méthode de type void")
	void logMethodAndArgument_voidMethod() throws Throwable {
		final ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		final MonServiceImpl clazz = new MonServiceImpl();
		when(pjp.getTarget()).thenReturn(clazz);
		final Logger logger = mock(Logger.class);
		when(appLoggerFactory.getLogger(clazz.getClass())).thenReturn(logger);
		when(logger.isDebugEnabled()).thenReturn(true);

		MethodSignature signature = mock(MethodSignature.class);
		when(pjp.getSignature()).thenReturn(signature);
		when(signature.getName()).thenReturn("nom méthode");
		when(signature.getReturnType()).thenReturn(Void.class);

		final Object[] args = new Object[] { "1", "2", "3" };
		when(pjp.getArgs()).thenReturn(args);

		final Object expectedReturnedValue = new Object();
		when(pjp.proceed()).thenReturn(expectedReturnedValue);

		Assertions.assertEquals(expectedReturnedValue, logMethodAndArgumentAspect.logMethodAndArgument(pjp));

		verify(logger).debug("Appel de la méthode nom méthode avec les arguments : ['1', '2', '3']");
		verify(logger).debug("Fin de la méthode {}", "nom méthode");
	}

	@Test
	@DisplayName("Logguer le nom et les paramètres d'une méthode qui n'est pas de type void")
	void logMethodAndArgument_nonVoidMethod() throws Throwable {
		final ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		final MonServiceImpl clazz = new MonServiceImpl();
		when(pjp.getTarget()).thenReturn(clazz);
		final Logger logger = mock(Logger.class);
		when(appLoggerFactory.getLogger(clazz.getClass())).thenReturn(logger);
		when(logger.isDebugEnabled()).thenReturn(true);

		MethodSignature signature = mock(MethodSignature.class);
		when(pjp.getSignature()).thenReturn(signature);
		when(signature.getName()).thenReturn("nom méthode");
		when(signature.getReturnType()).thenReturn(Long.class);

		final Object[] args = new Object[] { "1", "2", "3" };
		when(pjp.getArgs()).thenReturn(args);

		final Object expectedReturnedValue = new Object();
		when(pjp.proceed()).thenReturn(expectedReturnedValue);

		Assertions.assertEquals(expectedReturnedValue, logMethodAndArgumentAspect.logMethodAndArgument(pjp));

		verify(logger).debug("Appel de la méthode nom méthode avec les arguments : ['1', '2', '3']");
		verify(logger).debug("Fin de la méthode {} avec le retour [{}]", "nom méthode", expectedReturnedValue);
	}

	@Test
	@DisplayName("L'ordre de l'aspect est LOG_METHOD_NAME")
	void getLogAspectEnum() {
		Assertions.assertEquals(LOG_METHOD_NAME, logMethodAndArgumentAspect.getLogAspectEnum());
	}

	private class MonServiceImpl {

		void faireQuelqueChoseSansRetour(Long nombre, String texte) {
		}

		void faireQuelqueChoseAvecRetour(Long nombre, String texte) {
		}
	}
}