package fr.deroffal.portail.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionTimeAspect extends AbstractLogingAspect {

	private static final int NANO_TO_MILI_SEC = 1000000;

	@Around(TARGET)
	public Object logExecutionTime(final ProceedingJoinPoint pjp) throws Throwable {
		final Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
		final String methodName = pjp.getSignature().getName();

		final long before = System.nanoTime();
		final Object returnedValue = pjp.proceed();
		final long after = System.nanoTime();

		final long time = (after - before) / NANO_TO_MILI_SEC;

		logger.info("Le temps d'exécution de la méthode {} est de {} ms", methodName, time);
		return returnedValue;
	}

	@Override
	LogAspectEnum getLogAspectEnum() {
		return LogAspectEnum.LOG_EXECUTION_TIME;
	}
}
