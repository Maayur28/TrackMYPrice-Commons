package io.github.maayur28.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {

    private String url;

    private BigDecimal alertPrice;

    private Boolean isPriceAlert;

    private Boolean isStockAlert;
}
