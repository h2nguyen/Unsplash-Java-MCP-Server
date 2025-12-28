package de.nextviets.mcps.unsplash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record Photo(
    String id,
    String slug,
    @JsonProperty("alternative_slugs") Map<String, String> alternativeSlugs,
    @JsonProperty("created_at") String createdAt,
    @JsonProperty("updated_at") String updatedAt,
    @JsonProperty("promoted_at") String promotedAt,
    int width,
    int height,
    String color,
    @JsonProperty("blur_hash") String blurHash,
    String description,
    @JsonProperty("alt_description") String altDescription,
    List<Breadcrumb> breadcrumbs,
    Urls urls,
    Links links,
    int likes,
    @JsonProperty("liked_by_user") boolean likedByUser,
    boolean bookmarked,
    @JsonProperty("current_user_collections") List<Object> currentUserCollections,
    Sponsorship sponsorship,
    @JsonProperty("topic_submissions") Map<String, TopicSubmission> topicSubmissions,
    @JsonProperty("asset_type") String assetType,
    User user
) {
    public record Breadcrumb(
        String slug,
        String title,
        int index,
        String type
    ) {}

    public record Urls(
        String raw,
        String full,
        String regular,
        String small,
        String thumb,
        @JsonProperty("small_s3") String smallS3
    ) {}

    public record Links(
        String self,
        String html,
        String download,
        @JsonProperty("download_location") String downloadLocation,
        String photos,
        String likes,
        String portfolio
    ) {}

    public record TopicSubmission(
        String status,
        @JsonProperty("approved_on") String approvedOn
    ) {}

    public record Sponsorship(
        @JsonProperty("impression_urls") List<String> impressionUrls,
        String tagline,
        @JsonProperty("tagline_url") String taglineUrl,
        User sponsor
    ) {}

    public record User(
        String id,
        @JsonProperty("updated_at") String updatedAt,
        String username,
        String name,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("twitter_username") String twitterUsername,
        @JsonProperty("portfolio_url") String portfolioUrl,
        String bio,
        String location,
        Links links,
        @JsonProperty("profile_image") ProfileImage profileImage,
        @JsonProperty("instagram_username") String instagramUsername,
        @JsonProperty("total_collections") int totalCollections,
        @JsonProperty("total_likes") int totalLikes,
        @JsonProperty("total_photos") int totalPhotos,
        @JsonProperty("total_free_photos") int totalFreePhotos,
        @JsonProperty("total_promoted_photos") int totalPromotedPhotos,
        @JsonProperty("total_illustrations") int totalIllustrations,
        @JsonProperty("total_free_illustrations") int totalFreeIllustrations,
        @JsonProperty("total_promoted_illustrations") int totalPromotedIllustrations,
        @JsonProperty("accepted_tos") boolean acceptedTos,
        @JsonProperty("for_hire") boolean forHire,
        Social social
    ) {}

    public record ProfileImage(
        String small,
        String medium,
        String large
    ) {}

    public record Social(
        @JsonProperty("instagram_username") String instagramUsername,
        @JsonProperty("portfolio_url") String portfolioUrl,
        @JsonProperty("twitter_username") String twitterUsername,
        @JsonProperty("paypal_email") String paypalEmail
    ) {}
}
