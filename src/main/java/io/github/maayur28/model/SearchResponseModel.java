package io.github.maayur28.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
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

    @JsonCreator
    public SearchResponseModel(@JsonProperty("title") String title,
                               @JsonProperty("image") String image,
                               @JsonProperty("rating") String rating,
                               @JsonProperty("discountPrice") BigDecimal discountPrice,
                               @JsonProperty("originalPrice") BigDecimal originalPrice,
                               @JsonProperty("domain") String domain,
                               @JsonProperty("url") String url) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.discountPrice = discountPrice;
        this.originalPrice = originalPrice;
        this.domain = domain;
        this.url = url;
    }
}
