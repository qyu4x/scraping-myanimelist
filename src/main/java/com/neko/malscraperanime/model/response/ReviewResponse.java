package com.neko.malscraperanime.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private String username;

    private String animeTitle;

    private String imageURL;

    private String tags;

    private String comment;

    private String reviewedAt;

}
