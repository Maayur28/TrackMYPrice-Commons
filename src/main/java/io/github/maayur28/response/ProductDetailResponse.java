package io.github.maayur28.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.maayur28.model.Rating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailResponse {

    private String productId;

    @NotEmpty
    private String title;

    @Positive
    @Size(min = 1)
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?")
    private BigDecimal discountPrice;

    @Positive
    @Size(min = 1)
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?")
    private BigDecimal originalPrice;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String image;

    private List<String> subImages;

    private String badge;

    private Rating rating;

    private List<String> traits;

    @NotEmpty
    @Pattern(regexp = "AMAZON|FLIPKART")
    private String domain;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String url;

    private boolean inStock;

    private String lastUpdatedAt;
}
