package de.nextviets.mcps.unsplash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record UnsplashSearchResult(
    int total,
    @JsonProperty("total_pages") int totalPages,
    List<Photo> results
) {}
