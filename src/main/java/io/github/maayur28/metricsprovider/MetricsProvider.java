package io.github.maayur28.metricsprovider;

public interface MetricsProvider {
    void incrementCount(String metricName);
}
