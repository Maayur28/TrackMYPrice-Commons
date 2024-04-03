package io.github.maayur28.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ExtendedProductDetailModel {

    private String productId;

    @NotEmpty
    private String title;

    private String code;

    private BigDecimal discountPrice;

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

    private List<String> urlSKUs;
}
