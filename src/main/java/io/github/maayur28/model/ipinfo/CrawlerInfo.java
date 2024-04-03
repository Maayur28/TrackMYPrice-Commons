package io.github.maayur28.model.ipinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrawlerInfo {
    private boolean isCrawler;
    private String name;
    private String owner;
    private String category;
    private String url;
    private String lastSeen;
}