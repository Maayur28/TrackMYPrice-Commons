package io.github.maayur28.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "PriceAlert")
public class PriceAlertEntity {

    @DynamoDBHashKey(attributeName = "email")
    private String email;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "productIdIndex", attributeName = "productId")
    private String productId;

    @DynamoDBAttribute(attributeName = "alertPrice")
    private BigDecimal alertPrice;

    @DynamoDBAttribute(attributeName = "lastEmailSentPrice")
    private BigDecimal lastEmailSentPrice;

    @DynamoDBRangeKey(attributeName = "alertDate")
    private String alertDate;

    @DynamoDBAttribute(attributeName = "priceAlert")
    private Boolean priceAlert;

    @DynamoDBAttribute(attributeName = "inStockAlert")
    private Boolean inStockAlert;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "priorityIndex", attributeName = "priority")
    private Integer priority = 3;
}
