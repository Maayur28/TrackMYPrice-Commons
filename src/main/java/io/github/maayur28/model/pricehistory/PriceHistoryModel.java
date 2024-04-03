package io.github.maayur28.model.pricehistory;

import io.github.maayur28.model.PriceMapDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryModel {
    private String url;
    private String productId;
    private PriceMapDTO price;
}
