package io.github.maayur28.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TimeStampPriceHistory")
@TimeSeries(timeField = "timestamp", metaField = "productId")
public class PriceHistoryEntity {

    @Id
    private String id;

    @Field("timestamp")
    private Instant timestamp;

    @Field("price")
    private BigDecimal price;

    @Field("productId")
    private String productId;
}
