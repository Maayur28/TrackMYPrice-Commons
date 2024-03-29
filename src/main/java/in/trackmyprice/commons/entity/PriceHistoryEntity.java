package in.trackmyprice.commons.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import in.trackmyprice.commons.model.PriceMapDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@DynamoDBTable(tableName = "PriceHistoryTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryEntity {

    @DynamoDBHashKey
    private String productId;

    @DynamoDBAttribute
    private String url;

    @DynamoDBAttribute
    private List<PriceMapDTO> price;

    @DynamoDBAttribute
    private String createdAt;

    @DynamoDBAttribute
    private String updatedAt;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "lastScrapAt-index", attributeName = "lastScrapAt")
    private String lastScrapAt;
}
