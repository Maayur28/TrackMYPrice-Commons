package in.trackmyprice.commons.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlagInfo {
    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("unicode")
    private String unicode;

    @JsonProperty("png")
    private String png;

    @JsonProperty("svg")
    private String svg;
}