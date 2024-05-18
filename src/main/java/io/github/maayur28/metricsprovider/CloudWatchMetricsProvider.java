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
        try {
            MetricDatum datum = MetricDatum.builder()
                    .metricName(metricName)
                    .value(1.0)
                    .unit("Count")
                    .build();

            PutMetricDataRequest request = PutMetricDataRequest.builder()
                    .namespace(namespace)
                    .metricData(datum)
                    .build();

            // Publish the metric to CloudWatch
            cloudWatchClient.putMetricData(request);
        } catch (CloudWatchException e) {
            logger.error("CloudWatchException: {}", e.getMessage());
        }
    }

    @Override
    public void addTimer(String metricName, long value) {
        try {
            MetricDatum datum = MetricDatum.builder()
                    .metricName(metricName)
                    .value((double) value)
                    .unit(StandardUnit.MILLISECONDS)
                    .build();

            PutMetricDataRequest request = PutMetricDataRequest.builder()
                    .namespace(namespace)
                    .metricData(datum)
                    .build();

            // Publish the metric to CloudWatch
            cloudWatchClient.putMetricData(request);
        } catch (CloudWatchException e) {
            logger.error("CloudWatchException: {}", e.getMessage());
        }
    }
}
