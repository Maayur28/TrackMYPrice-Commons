package io.github.maayur28.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealsModel {

    private String title;

    private BigDecimal previousPrice;

    private String previousDate;

    private BigDecimal latestPrice;

    private String latestDate;

    private String image;

    private String badge;

    private Rating rating;

    private String domain;

    private String url;
}
