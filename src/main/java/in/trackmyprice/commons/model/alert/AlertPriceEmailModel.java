package in.trackmyprice.commons.model.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AlertPriceEmailModel {

    private BigDecimal alertPrice;

    private String email;

    private BigDecimal currentPrice;
}
