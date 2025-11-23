package io.github.maayur28.metricsprovider;

import java.util.Arrays;
import java.util.List;

public class CompositeMetricsProvider implements MetricsProvider {

    private final List<MetricsProvider> providers;

    public CompositeMetricsProvider(MetricsProvider... providers) {
        this.providers = Arrays.asList(providers);
    }

    @Override
    public void incrementCount(String metricName) {
        providers.forEach(p -> p.incrementCount(metricName));
    }

    @Override
    public void addTimer(String metricName, long value) {
        providers.forEach(p -> p.addTimer(metricName, value));
    }

    @Override
    public void gauge(String metricName, double value) {
        providers.forEach(p -> p.gauge(metricName, value));
    }
}