package io.management.ua.aop;

import io.management.ua.annotations.DefaultNumberValue;
import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class NumberAnnotationAspect {

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
                        double value = ((DefaultNumberValue) annotation).v();

                        switch (method.getParameters()[paramIndex].getType().getName()) {
                            case "java.lang.Long" -> args[paramIndex] = (long) value;
                            case "java.lang.Integer" -> args[paramIndex] = (int) value;
                            case "java.lang.Double" -> args[paramIndex] = value;
                            case "java.lang.Float" -> args[paramIndex] = (float) value;
                            case "java.lang.Short" -> args[paramIndex] = (short) value;
                            default -> throw new RuntimeException("Incompatible data type for usage with annotation DefaultNumberValue");
                        }
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
