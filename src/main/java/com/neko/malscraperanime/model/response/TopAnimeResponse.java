package com.neko.malscraperanime.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopAnimeResponse {

    private String japaneseTitle;

    private String englishTitle;

    private String coverImageUrl;

    private String type;

    private String episodes;

    private String status;

    private String aired;

    private String producers;

    private String studios;

    private String source;

    private String genres;

    private String duration;

    private String rating;

    private String score;

    private String ratingCount;

    private String ranked;

    private String popularity;

    private String members;

    private  String favorites;

    private String description;

}
