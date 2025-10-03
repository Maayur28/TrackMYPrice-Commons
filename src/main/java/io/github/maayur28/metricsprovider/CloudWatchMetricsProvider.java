package io.github.maayur28.metricsprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

public class CloudWatchMetricsProvider implements MetricsProvider {

    private static final Logger logger = LoggerFactory.getLogger(CloudWatchMetricsProvider.class);

    private final CloudWatchClient cloudWatchClient;
    private final String namespace;

    public CloudWatchMetricsProvider(CloudWatchClient cloudWatchClient, String namespace) {
        this.cloudWatchClient = cloudWatchClient;
        this.namespace = namespace;
    }

    @Override
    public void incrementCount(String metricName) {
        publishMetric(metricName, 1.0, StandardUnit.COUNT);
    }

    @Override
    public void addTimer(String metricName, long valueMillis) {
        publishMetric(metricName, (double) valueMillis, StandardUnit.MILLISECONDS);
    }

    @Override
    public void gauge(String metricName, double value) {
        publishMetric(metricName, value, StandardUnit.COUNT); // Gauge usually uses "Count" or "None"
    }

    private void publishMetric(String metricName, double value, StandardUnit unit) {
        try {
            MetricDatum datum = MetricDatum.builder()
                    .metricName(metricName)
                    .value(value)
                    .unit(unit)
                    .build();

            PutMetricDataRequest request = PutMetricDataRequest.builder()
                    .namespace(namespace)
                    .metricData(datum)
                    .build();

            cloudWatchClient.putMetricData(request);
        } catch (CloudWatchException e) {
            logger.error("CloudWatchException while publishing {}: {}", metricName, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while publishing {}: {}", metricName, e.getMessage(), e);
        }
    }
}
