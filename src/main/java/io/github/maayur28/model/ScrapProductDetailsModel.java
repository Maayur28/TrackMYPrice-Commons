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
public class ScrapProductDetailsModel {

    private String requestId;

    private String productId;

    private long sequenceId;

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
