package fr.deroffal.bibliotheque.api.logging;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static fr.deroffal.bibliotheque.api.logging.LogAspectOrder.LOG_METHOD_NAME;
import static java.util.stream.Collectors.joining;

@Aspect
@Component
@RequiredArgsConstructor
public class LogMethodAndArgumentAspect extends AbstractLogingAspect {

	private final AppLoggerFactory appLoggerFactory;

	@Around(TARGET_BASE_PACKAGE)
	public Object logMethodAndArgument(final ProceedingJoinPoint pjp) throws Throwable {
		final Logger logger = appLoggerFactory.getLogger(pjp.getTarget().getClass());
		if (!logger.isDebugEnabled()) {
			return pjp.proceed();
		}
		final Signature signature = pjp.getSignature();
		final String methodName = signature.getName();

		logger.debug(concatMethodNameAndArgument(methodName, pjp.getArgs()));

		final Object returnedValue = pjp.proceed();

		if (isVoidMethod(signature)) {
			logger.debug("Fin de la méthode {}", methodName);
		} else {
			logger.debug("Fin de la méthode {} avec le retour [{}]", methodName, returnedValue);
		}

		return returnedValue;
	}

	private String concatMethodNameAndArgument(final String methodName, final Object[] args) {
		final String joinedArgs = Stream.of(args)
				.map(Object::toString)
				.map(s -> "'" + s + "'")
				.collect(joining(", ", "[", "]"));
		return "Appel de la méthode " + methodName + " avec les arguments : " + joinedArgs + "";
	}

	private static boolean isVoidMethod(final Signature signature) {
		return Void.class.equals(((MethodSignature) signature).getReturnType());
	}

	@Override
	LogAspectOrder getLogAspectEnum() {
		return LOG_METHOD_NAME;
	}
}
