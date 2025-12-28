package de.nextviets.mcps.unsplash.controller;

import de.nextviets.mcps.unsplash.service.UnsplashService;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Controller;

/**
 * MCP Controller for Unsplash tools.
 * Exposes Unsplash API functionality as MCP tools.
 */
@Controller
public class UnsplashMcpController {

    private final UnsplashService unsplashService;

    public UnsplashMcpController(final UnsplashService unsplashService) {
        this.unsplashService = unsplashService;
    }

    @McpTool(name = "search-photos", description = "Search for photos on Unsplash by query")
    public String searchPhotos(
            @McpToolParam(description = "Search query") final String query,
            @McpToolParam(description = "Page number to retrieve (Optional; default: 1)", required = false) final Integer page,
            @McpToolParam(description = "Number of items per page (Optional; default: 20)", required = false) final Integer perPage,
            @McpToolParam(description = "How to sort the photos (Optional; default: relevant). Valid values are latest and relevant.", required = false) final String orderBy,
            @McpToolParam(description = "Collection ID('s) to narrow search. Optional. If multiple, comma-separated.", required = false) final String collections,
            @McpToolParam(description = "Limit results by content safety. (Optional; default: low). Valid values are low and high.", required = false) final String contentFilter,
            @McpToolParam(description = "Filter results by color. Optional. Valid values are: black_and_white, black, white, yellow, orange, red, purple, magenta, green, teal, and blue.", required = false) final String color,
            @McpToolParam(description = "Filter by photo orientation. Optional. Valid values: landscape, portrait, squarish", required = false) final String orientation,
            @McpToolParam(description = "Language of the query. Optional; default: en", required = false) final String lang
    ) {
        return unsplashService.searchPhotos(query, page, perPage, orderBy, collections, contentFilter, color, orientation, lang);
    }

    @McpTool(name = "list-photos", description = "List photos on Unsplash")
    public String listPhotos(
            @McpToolParam(description = "Page number to retrieve. (Optional; default: 1)", required = false) final Integer page,
            @McpToolParam(description = "Number of items per page. (Optional; default: 20)", required = false) final Integer perPage
    ) {
        return unsplashService.listPhotos(page, perPage);
    }
}
