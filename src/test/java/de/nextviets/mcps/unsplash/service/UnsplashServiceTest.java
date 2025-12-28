package de.nextviets.mcps.unsplash.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.hamcrest.Matchers.startsWith;

@RestClientTest(UnsplashService.class)
class UnsplashServiceTest {

    @Autowired
    private UnsplashService unsplashService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void searchPhotos_shouldOnlyIncludeNonNullParameters() {
        server.expect(requestTo(startsWith("https://api.unsplash.com/search/photos")))
                .andExpect(queryParam("query", "nature"))
                .andExpect(arg -> {
                    String uri = arg.getURI().toString();
                    if (uri.contains("page") || uri.contains("per_page") || uri.contains("order_by")) {
                        throw new AssertionError("Should not contain null parameters: " + uri);
                    }
                })
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        unsplashService.searchPhotos("nature", null, null, null, null, null, null, null, null);

        server.verify();
    }

    @Test
    void listPhotos_shouldOnlyIncludeNonNullParameters() {
        server.expect(requestTo(startsWith("https://api.unsplash.com/photos")))
                .andExpect(arg -> {
                    String uri = arg.getURI().toString();
                    if (uri.contains("page=") || uri.contains("per_page=")) {
                        throw new AssertionError("Should not contain null parameters: " + uri);
                    }
                })
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        unsplashService.listPhotos(null, null);

        server.verify();
    }
}
