package io.github.maayur28.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertPriceEmailSentModel {

    private String productId;

    private BigDecimal currentPrice;

}
