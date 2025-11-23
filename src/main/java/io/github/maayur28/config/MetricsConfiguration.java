package io.github.maayur28.config;

import io.github.maayur28.metricsprovider.CloudWatchMetricsProvider;
import io.github.maayur28.metricsprovider.CompositeMetricsProvider;
import io.github.maayur28.metricsprovider.InfluxMetricsProvider;
import io.github.maayur28.metricsprovider.MetricsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;

@Configuration
public class MetricsConfiguration {

    public MetricsConfiguration() {
    }

    @Bean
    @Primary
    public MetricsProvider metricsProvider(CloudWatchClient cloudWatchClient) {

        MetricsProvider cloudWatch = new CloudWatchMetricsProvider(cloudWatchClient, "TrackMyPrice");

        MetricsProvider influx = new InfluxMetricsProvider(
                "https://metrics.trackmyprice.in",
                System.getenv().getOrDefault("INFLUX_TOKEN","dummyToken"),
                "trackmyprice",
                "metrics",
                System.getenv().getOrDefault("SERVICE_NAME", "UnknownService")
        );

        return new CompositeMetricsProvider(cloudWatch, influx);
    }

}
