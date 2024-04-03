package io.github.maayur28.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseModel {
    @NotEmpty
    private String title;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String image;

    private String rating;

    private BigDecimal discountPrice;

    private BigDecimal originalPrice;

    @NotEmpty
    @Pattern(regexp = "AMAZON|FLIPKART")
    private String domain;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String url;
}
