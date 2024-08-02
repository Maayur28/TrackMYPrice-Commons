package io.github.maayur28.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maayur28.model.Rating;

public class RatingConverter implements DynamoDBTypeConverter<String, Rating> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(Rating rating) {
        try {
            return objectMapper.writeValueAsString(rating);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Rating to JSON string", e);
        }
    }

    @Override
    public Rating unconvert(String ratingString) {
        try {
            return objectMapper.readValue(ratingString, Rating.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to Rating", e);
        }
    }
}
