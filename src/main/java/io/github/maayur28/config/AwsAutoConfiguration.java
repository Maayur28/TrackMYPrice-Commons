package io.github.maayur28.config;

import io.github.maayur28.metricsprovider.CloudWatchMetricsProvider;
import io.github.maayur28.metricsprovider.MetricsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.ssm.SsmClient;


@AutoConfiguration
@ConditionalOnProperty(value = "trackmyprice.aws.enabled", havingValue = "true", matchIfMissing = true)
public class AwsAutoConfiguration {

    private static final Region REGION = Region.AP_SOUTH_1;
    private static final String LOCAL_DEV_DEFAULT_PROFILE = "trackmyprice-dev";

    private DefaultCredentialsProvider creds() {
        return DefaultCredentialsProvider.builder().build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(REGION)
                .credentialsProvider(creds())
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public SsmClient ssmClient() {
        return SsmClient.builder()
                .region(REGION)
                .credentialsProvider(creds())
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public CloudWatchClient cloudWatchClient() {
        return CloudWatchClient.builder()
                .region(REGION)
                .credentialsProvider(creds())
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public LambdaClient lambdaClient() {
        return LambdaClient.builder()
                .region(REGION)
                .credentialsProvider(creds())
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public LambdaAsyncClient lambdaAsyncClient() {
        return LambdaAsyncClient.builder()
                .region(REGION)
                .credentialsProvider(creds())
                .build();
    }


    @Bean
    @ConditionalOnMissingBean
    public MetricsProvider metricsProvider(
            CloudWatchClient cloudWatchClient,
            @Value("${trackmyprice.service.name}") String serviceName
    ) {
        return new CloudWatchMetricsProvider(cloudWatchClient, serviceName);
    }
}
