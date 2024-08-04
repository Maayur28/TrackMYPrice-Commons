package io.github.maayur28.entity;

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

@Document(collection = "ProductDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsEntity {

    @Id
    private String productId;

    @Indexed(unique = true)
    private long sequenceId;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotNull(message = "Discount price is required")
    @Positive(message = "Discount price must be greater than zero")
    private BigDecimal discountPrice;

    @NotNull(message = "Original price is required")
    @Positive(message = "Original price must be greater than zero")
    private BigDecimal originalPrice;

    @NotEmpty(message = "Image URL is required")
    @Pattern(regexp = "https://.*", message = "Image URL must start with 'https://'")
    private String image;

    private List<String> subImages;

    private String badge;

    private Rating rating;

    private List<String> traits;

    @NotEmpty(message = "Domain is required")
    @Pattern(regexp = "AMAZON|FLIPKART", message = "Domain must be 'AMAZON' or 'FLIPKART'")
    private String domain;

    @NotEmpty(message = "URL is required")
    @Pattern(regexp = "https://.*", message = "URL must start with 'https://'")
    private String url;

    private String createdAt;

    private String updatedAt;

    private String lastScrapAt;

    private Boolean inStock;

    public ProductDetailsModel toProductDetailsModel() {
        ProductDetailsModel response = new ProductDetailsModel();
        response.setProductId(this.getProductId());
        response.setSequenceId(this.getSequenceId());
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
