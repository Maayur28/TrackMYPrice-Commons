package io.github.maayur28.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryExtensionResponse {

    private PriceAnalyserResponse analysis;
    private PriceResponse minimumPrice;
    private PriceResponse lastPriceDrop;
    private PriceResponse maximumPrice;
    private Map<String, BigDecimal> price;
}
