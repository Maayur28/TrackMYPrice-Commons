package io.github.maayur28.helper;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

@Service
public class ParameterStoreHelper {

    private final SsmClient ssmClient;

    public ParameterStoreHelper(SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    public final String getParameterValue(String key) {
        try {
            GetParameterRequest request = GetParameterRequest.builder()
                    .name(key)
                    .withDecryption(true)
                    .build();

            GetParameterResponse resp = ssmClient.getParameter(request);
            return resp.parameter().value();
        } catch (SsmException e) {
            // log or rethrow as your app's exception
            throw e;
        }
    }
}
