package in.trackmyprice.commons.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.trackmyprice.commons.model.Rating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailsModel {

    private String productId;

    private long sequenceId;

    private String code;

    @NotEmpty
    private String title;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String image;

    private List<String> traits;

    private List<String> subImages;

    private String badge;

    private Rating rating;

    @NotEmpty
    @Pattern(regexp = "AMAZON|FLIPKART")
    private String domain;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String url;

    private Boolean inStock;

    private String lastUpdatedAt;
}
