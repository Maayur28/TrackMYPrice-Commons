package io.github.maayur28.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "PriceHistory")
public class PriceHistoryEntity {

    @DynamoDBHashKey(attributeName = "productId")
    private String productId;

    @DynamoDBRangeKey(attributeName = "logTimeStamp")
    private String logTimeStamp;

    @DynamoDBAttribute(attributeName = "price")
    private BigDecimal price;

}
