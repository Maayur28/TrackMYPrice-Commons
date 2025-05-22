package io.github.maayur28.metrics;

import io.github.maayur28.metrics.annotations.Failure;
import io.github.maayur28.metrics.annotations.Latency;
import io.github.maayur28.metrics.annotations.Request;
import io.github.maayur28.metricsprovider.MetricsProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricsAspect {

    private final MetricsProvider metricsProvider;

    public MetricsAspect(MetricsProvider metricsProvider) {
        this.metricsProvider = metricsProvider;
    }

    @Pointcut("@annotation(io.github.maayur28.metrics.annotations.Request)")
    public void requestAnnotated() {}

    @Pointcut("@annotation(io.github.maayur28.metrics.annotations.Failure)")
    public void failureAnnotated() {}

    @Pointcut("@annotation(io.github.maayur28.metrics.annotations.Latency)")
    public void latencyAnnotated() {}


    @Around("execution(* *(..)) && (requestAnnotated() || failureAnnotated() || latencyAnnotated())")
    public Object captureMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getMethod().getName();
        String base = className + "." + methodName;

        boolean isRequest = signature.getMethod().isAnnotationPresent(Request.class);
        boolean isFailure = signature.getMethod().isAnnotationPresent(Failure.class);
        boolean isLatency = signature.getMethod().isAnnotationPresent(Latency.class);

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if (isRequest) {
                metricsProvider.incrementCount(base + ".request");
            }
            return result;
        } catch (Exception e) {
            if (isFailure) {
                metricsProvider.incrementCount(base + ".failure");
            }
            throw e;
        } finally {
            if (isLatency) {
                metricsProvider.addTimer(base + ".latency", System.currentTimeMillis() - start);
            }
        }
    }
}
