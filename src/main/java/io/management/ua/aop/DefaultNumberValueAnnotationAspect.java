package io.management.ua.aop;

import io.management.ua.annotations.DefaultNumberValue;
import io.management.ua.exceptions.AnnotationProcessingException;
import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class DefaultNumberValueAnnotationAspect {

    @Around("execution(* *(.., @io.management.ua.annotations.DefaultNumberValue (*), ..))")
    public Object processNumberAnnotatedParameters(@NonNull ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Object[] args = new Object[method.getParameterCount()];

        for (int paramIndex = 0; paramIndex < method.getParameters().length; paramIndex++) {
            args[paramIndex] = joinPoint.getArgs().length > paramIndex
                    ? joinPoint.getArgs()[paramIndex] : null;

            if (joinPoint.getArgs().length <= paramIndex || joinPoint.getArgs()[paramIndex] == null) {
                Annotation[] annotations = method.getParameters()[paramIndex].getAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation instanceof DefaultNumberValue) {
                        int number = ((DefaultNumberValue) annotation).number();
                        Parameter parameter = method.getParameters()[paramIndex];

                        if (Number.class.isAssignableFrom(parameter.getType())) {
                            args[paramIndex] = number;
                        } else {
                            throw new AnnotationProcessingException(String.format("Annotation can not be processed because used not with number data type variable at %s:%s %s",
                                    joinPoint.getSignature().getClass().getName(),
                                    joinPoint.getSourceLocation().getLine(),
                                    joinPoint.getSignature().getName()));
                        }
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
