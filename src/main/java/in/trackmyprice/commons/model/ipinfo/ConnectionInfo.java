package in.trackmyprice.commons.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionInfo {
    @JsonProperty("autonomous_system_number")
    private int autonomousSystemNumber;

    @JsonProperty("autonomous_system_organization")
    private String autonomousSystemOrganization;

    @JsonProperty("connection_type")
    private String connectionType;

    @JsonProperty("isp_name")
    private String ispName;

    @JsonProperty("organization_name")
    private String organizationName;
}