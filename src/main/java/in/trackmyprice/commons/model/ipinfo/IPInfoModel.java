package in.trackmyprice.commons.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IPInfoModel {

    @NotEmpty
    private String requestId;

    @NotEmpty
    private String requestURL;

    @JsonProperty("city")
    private String city;

    @JsonProperty("city_geoname_id")
    private int cityGeonameId;

    @JsonProperty("region")
    private String region;

    @JsonProperty("region_iso_code")
    private String regionIsoCode;

    @JsonProperty("region_geoname_id")
    private int regionGeonameId;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_geoname_id")
    private int countryGeonameId;

    @JsonProperty("country_is_eu")
    private boolean countryIsEU;

    @JsonProperty("continent")
    private String continent;

    @JsonProperty("continent_code")
    private String continentCode;

    @JsonProperty("continent_geoname_id")
    private int continentGeonameId;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("security")
    private SecurityInfo securityInfo;

    @JsonProperty("timezone")
    private TimezoneInfo timezoneInfo;

    @JsonProperty("flag")
    private FlagInfo flagInfo;

    @JsonProperty("currency")
    private CurrencyInfo currencyInfo;

    @JsonProperty("connection")
    private ConnectionInfo connectionInfo;

    @JsonProperty("useragentInfo")
    private UserAgentInfo userAgentInfo;

    @NotEmpty
    private String addedAt;

}
