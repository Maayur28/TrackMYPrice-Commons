package io.github.maayur28.request;

import io.github.maayur28.model.PriceMapDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryRequest {

    private String productId;

    private String url;

    private List<PriceMapDTO> price;

    @NotEmpty
    private String createdAt;

    @NotEmpty
    private String updatedAt;
}
