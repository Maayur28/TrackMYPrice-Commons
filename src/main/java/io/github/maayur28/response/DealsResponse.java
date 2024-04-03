package io.github.maayur28.response;

import io.github.maayur28.model.DealsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealsResponse {

    private List<DealsModel> lightningDeals;

    private List<DealsModel> hotDeals;

    private List<DealsModel> deals;

}
