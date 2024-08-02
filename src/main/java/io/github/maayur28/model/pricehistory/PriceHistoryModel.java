package io.github.maayur28.model.pricehistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryModel {
    private String productId;
    private String logTimeStamp;
    private BigDecimal price;
}
