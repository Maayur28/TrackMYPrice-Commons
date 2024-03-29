package in.trackmyprice.commons.model.pricehistory;

import in.trackmyprice.commons.model.PriceMapDTO;
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
