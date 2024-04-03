package io.github.maayur28.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceAnalyserResponse {

    private Double status;

    private String message;
}
