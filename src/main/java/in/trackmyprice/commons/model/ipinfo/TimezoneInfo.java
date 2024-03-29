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
public class TimezoneInfo {
    @JsonProperty("name")
    private String name;

    @JsonProperty("abbreviation")
    private String abbreviation;

    @JsonProperty("gmt_offset")
    private int gmtOffset;

    @JsonProperty("current_time")
    private String currentTime;

    @JsonProperty("is_dst")
    private boolean isDst;
}