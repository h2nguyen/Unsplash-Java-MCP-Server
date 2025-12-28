package de.nextviets.mcps.unsplash.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Service
public class UnsplashService {

    private static final Logger log = LoggerFactory.getLogger(UnsplashService.class);
    private final RestClient restClient;

    public UnsplashService(final RestClient.Builder builder, @Value("${unsplash.access-key}") final String accessKey) {
        this.restClient = builder
                .baseUrl("https://api.unsplash.com")
                .defaultHeader("Authorization", "Client-ID " + accessKey)
                .build();
    }

    public String searchPhotos(
            final String query,
            final Integer page,
            final Integer perPage,
            final String orderBy,
            final String collections,
            final String contentFilter,
            final String color,
            final String orientation,
            final String lang
    ) {
        log.info("Searching photos with query: {}", query);
        try {
            return restClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/search/photos")
                                .queryParam("query", query);
                        if (page != null && page != 0) uriBuilder.queryParam("page", page);
                        if (perPage != null && perPage != 0) uriBuilder.queryParam("per_page", perPage);
                        if (StringUtils.hasText(orderBy)) uriBuilder.queryParam("order_by", orderBy);
                        if (StringUtils.hasText(collections)) uriBuilder.queryParam("collections", collections);
                        if (StringUtils.hasText(contentFilter)) uriBuilder.queryParam("content_filter", contentFilter);
                        if (StringUtils.hasText(color)) uriBuilder.queryParam("color", color);
                        if (StringUtils.hasText(orientation)) uriBuilder.queryParam("orientation", orientation);
                        if (StringUtils.hasText(lang)) uriBuilder.queryParam("lang", lang);
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .body(String.class);
        } catch (final Exception e) {
            log.error("Error searching photos: {}", e.getMessage());
            return "Error searching photos: " + e.getMessage();
        }
    }

    public String listPhotos(
            final Integer page,
            final Integer perPage
    ) {
        log.info("Listing photos, page: {}, perPage: {}", page, perPage);
        try {
            return restClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/photos");
                        if (page != null && page > 0) uriBuilder.queryParam("page", page);
                        if (perPage != null && perPage > 0) uriBuilder.queryParam("per_page", perPage);
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .body(String.class);
        } catch (final Exception e) {
            log.error("Error listing photos: {}", e.getMessage());
            return "Error listing photos: " + e.getMessage();
        }
    }
}
