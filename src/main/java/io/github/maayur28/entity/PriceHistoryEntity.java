package io.github.maayur28.entity;

import io.github.maayur28.model.PriceMapDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PriceHistory")
public class PriceHistoryEntity {

    @Id
    private String productId;

    private String url;

    private List<PriceMapDTO> price;

    private String createdAt;

    private String updatedAt;

    @Indexed
    private String lastScrapAt;
}

