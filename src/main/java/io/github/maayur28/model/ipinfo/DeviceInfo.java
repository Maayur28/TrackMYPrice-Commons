package io.github.maayur28.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfo {
    private String name;
    private String type;
    private String brand;
    private String screenResolutionWidth;
    private String screenResolutionHeight;
    private String viewportWidth;
    private String viewportHeight;
    private String language;
    private boolean isTouchScreen;
    private boolean isMobile;
}