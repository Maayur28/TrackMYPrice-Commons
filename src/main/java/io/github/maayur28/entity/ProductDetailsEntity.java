package io.github.maayur28.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import io.github.maayur28.converter.RatingConverter;
import io.github.maayur28.model.Rating;
import io.github.maayur28.model.product.ProductDetailsModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "ProductDetails")
public class ProductDetailsEntity {

    @DynamoDBHashKey(attributeName = "productId")
    private String productId;

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "discountPrice")
    private BigDecimal discountPrice;

    @DynamoDBAttribute(attributeName = "originalPrice")
    private BigDecimal originalPrice;

    @DynamoDBAttribute(attributeName = "image")
    private String image;

    @DynamoDBAttribute(attributeName = "subImages")
    private List<String> subImages;

    @DynamoDBAttribute(attributeName = "badge")
    private String badge;

    @DynamoDBTypeConverted(converter = RatingConverter.class)
    @DynamoDBAttribute(attributeName = "rating")
    private Rating rating;

    @DynamoDBAttribute(attributeName = "traits")
    private List<String> traits;

    @DynamoDBAttribute(attributeName = "domain")
    private String domain;

    @DynamoDBAttribute(attributeName = "url")
    private String url;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBRangeKey(attributeName = "updatedAt")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "inStock")
    private Boolean inStock;

    public ProductDetailsModel toProductDetailsModel() {
        ProductDetailsModel response = new ProductDetailsModel();
        response.setProductId(this.getProductId());
        response.setTitle(this.getTitle());
        response.setDiscountPrice(this.getDiscountPrice());
        response.setOriginalPrice(this.getOriginalPrice());
        response.setImage(this.getImage());
        response.setSubImages(this.getSubImages());
        response.setBadge(this.getBadge());
        response.setRating(this.getRating());
        response.setTraits(this.getTraits());
        response.setDomain(this.getDomain());
        response.setUrl(this.getUrl());
        response.setInStock(this.getInStock());
        response.setLastUpdatedAt(this.getUpdatedAt());
        return response;
    }
}
