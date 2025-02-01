package io.github.maayur28.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSuggestionModel {

    private String title;

    private String image;

    private String domain;

    private String url;
}
