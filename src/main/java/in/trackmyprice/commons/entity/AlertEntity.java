package in.trackmyprice.commons.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "Alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertEntity {

    @Id
    private String alertId;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String email;

    @NotNull
    @Positive
    @Size(min = 1)
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?")
    private BigDecimal alertPrice;

    private BigDecimal lastEmailSentPrice;

    private String alertDate;

    private Boolean priceAlert;

    private Boolean inStockAlert;

    private Integer priority = 3;
}
