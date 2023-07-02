package com.neko.malscraperanime.service;

import com.neko.malscraperanime.model.response.ReviewResponse;
import com.neko.malscraperanime.model.response.TopAnimeResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface MalScraperService {

    Set<TopAnimeResponse> topAnime(Integer count) throws IOException;
    List<ReviewResponse> reviewsList(Integer page) throws IOException;


}
