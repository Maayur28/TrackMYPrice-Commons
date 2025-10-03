package io.github.maayur28.metricsprovider;

public interface MetricsProvider {
    void incrementCount(String metricName);

    void addTimer(String metricName, long value);

    void gauge(String metricName, double value);
}
