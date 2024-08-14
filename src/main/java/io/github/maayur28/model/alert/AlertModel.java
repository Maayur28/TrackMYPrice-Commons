package io.github.maayur28.model.alert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertModel {

    private String productId;

    private String title;

    private BigDecimal alertPrice;

    private String alertDate;

    private BigDecimal currentPrice;

    private String currentDate;

    private String image;

    private String domain;

    private String url;
}
