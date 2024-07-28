package io.github.maayur28.model;

import io.github.maayur28.enums.DealType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealsOfTheDayModel {

    @Id
    private String productId;

    @NotEmpty
    private String title;

    @NotNull
    @Positive
    @Size(min = 1)
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?")
    private BigDecimal previousPrice;

    @NotEmpty
    private String previousDate;

    @NotNull
    @Positive
    @Size(min = 1)
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?")
    private BigDecimal latestPrice;

    @NotEmpty
    private String latestDate;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String image;

    private String badge;

    private Rating rating;

    @NotEmpty
    @Pattern(regexp = "AMAZON|FLIPKART")
    private String domain;

    @NotEmpty
    @Pattern(regexp = "https://.*")
    private String url;

    private DealType dealType;

    private String code;
}
