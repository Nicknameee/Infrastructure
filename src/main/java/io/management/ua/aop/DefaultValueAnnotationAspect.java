package io.management.ua.aop;

import io.management.ua.annotations.DefaultValue;
import io.management.ua.utility.UtilManager;
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
public class DefaultValueAnnotationAspect {

    @Around("execution(* *(.., @io.management.ua.annotations.DefaultValue (*), ..))")
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
                    if (annotation instanceof DefaultValue) {
                        String value = ((DefaultValue) annotation).value();
                        args[paramIndex] = UtilManager.objectMapper().readValue(value, method.getParameters()[paramIndex].getType());
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
