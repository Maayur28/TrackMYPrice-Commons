package io.github.maayur28.model.pricehistory;

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

    private List<PriceHistoryModel> price;

    @NotEmpty
    private String createdAt;

    @NotEmpty
    private String updatedAt;
}
