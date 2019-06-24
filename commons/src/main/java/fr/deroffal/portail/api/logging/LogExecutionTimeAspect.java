package fr.deroffal.portail.api.logging;

import fr.deroffal.portail.api.util.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

import static fr.deroffal.portail.api.logging.LogAspectOrder.LOG_EXECUTION_TIME;

@Aspect
@Component
public class LogExecutionTimeAspect extends AbstractLogingAspect {

	@Autowired
	private DateUtils dateUtils;

	@Autowired
	private AppLoggerFactory appLoggerFactory;

	@Around(TARGET_BASE_PACKAGE)
	public Object logExecutionTime(final ProceedingJoinPoint pjp) throws Throwable {
		final Logger logger = appLoggerFactory.getLogger(pjp.getTarget().getClass());
		final String methodName = pjp.getSignature().getName();

		final LocalDateTime before = dateUtils.newLocalDateTime();
		final Object returnedValue = pjp.proceed();
		final LocalDateTime after = dateUtils.newLocalDateTime();

		logger.debug("Le temps d'exécution de la méthode {} est de {} ms", methodName, Duration.between(before, after).toMillis());
		return returnedValue;
	}

	@Override
	LogAspectOrder getLogAspectEnum() {
		return LOG_EXECUTION_TIME;
	}
}
