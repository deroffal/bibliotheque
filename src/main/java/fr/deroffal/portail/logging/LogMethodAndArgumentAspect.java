package fr.deroffal.portail.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogMethodAndArgumentAspect extends AbstractLogingAspect {

	@Around(TARGET_BASE_PACKAGE)
	public Object logMethodAndArgument(final ProceedingJoinPoint pjp) throws Throwable {
		final Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());

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
		final StringBuilder sb = new StringBuilder("Appel de la méthode ");
		sb.append(methodName).append(" avec les arguments : [");
		for (int i = 0; i < args.length; i++) {
			final Object o = args[i];
			sb.append("'").append(o).append("'");
			sb.append((i == args.length - 1) ? "" : ", ");
		}
		sb.append("]");
		return sb.toString();
	}

	private boolean isVoidMethod(final Signature signature) {
		return "void".equals(((MethodSignature) signature).getReturnType().getName());
	}

	@Override
	LogAspectEnum getLogAspectEnum() {
		return LogAspectEnum.LOG_METHOD_NAME;
	}
}
