package io.github.maayur28.helper;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import org.springframework.stereotype.Service;

@Service
public class ParameterStoreHelper {

    private final AWSSimpleSystemsManagement ssmClient;

    public ParameterStoreHelper(AWSSimpleSystemsManagement ssmClient) {
        this.ssmClient = ssmClient;
    }

    public String getParameterValue(String key) {
        GetParameterRequest parameterRequest = new GetParameterRequest().withName(key).withWithDecryption(true);
        GetParameterResult parameterResult = ssmClient.getParameter(parameterRequest);
        return parameterResult.getParameter().getValue();
    }
}
