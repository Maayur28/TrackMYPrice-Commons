package in.trackmyprice.commons.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.trackmyprice.commons.model.ipinfo.IPInfoModel;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "IPInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpInfoEntity {

    @Id
    @JsonProperty("ip_address")
    private String ipAddress;

    private Integer id;

    private List<IPInfoModel> ipInfoList;

    @NotEmpty
    private String createdAt;

    @NotEmpty
    private String updatedAt;
}
