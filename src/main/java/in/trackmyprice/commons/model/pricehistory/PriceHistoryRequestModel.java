package in.trackmyprice.commons.model.pricehistory;

import in.trackmyprice.commons.model.PriceMapDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryRequestModel {

    private String productId;

    private String url;

    private List<PriceMapDTO> price;

    @NotEmpty
    private String createdAt;

    @NotEmpty
    private String updatedAt;
}
