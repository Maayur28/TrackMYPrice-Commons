package io.github.maayur28.response;

import io.github.maayur28.model.SearchResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    List<SearchResponseModel> searchResponse;
}
