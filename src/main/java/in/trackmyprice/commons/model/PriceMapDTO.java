package in.trackmyprice.commons.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class PriceMapDTO {

    @DynamoDBAttribute
    private String timestamp;

    @DynamoDBAttribute
    private BigDecimal price;
}
