package io.github.maayur28.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class PriceAlertEntity {

    @Id
    private String alertId;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String email;

    @NotNull
    @Positive
    private BigDecimal alertPrice;

    private BigDecimal lastEmailSentPrice;

    private String alertDate;

    private Boolean priceAlert;

    private Boolean inStockAlert;

    private Integer priority = 3;
}