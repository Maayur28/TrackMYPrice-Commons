package in.trackmyprice.commons.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAgentInfo {
    private String userAgent;
    private BrowserInfo browser;
    private OperatingSystemInfo operatingSystem;
    private DeviceInfo device;
    private CrawlerInfo crawler;
}
