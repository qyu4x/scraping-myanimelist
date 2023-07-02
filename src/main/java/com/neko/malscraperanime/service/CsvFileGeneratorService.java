package com.neko.malscraperanime.service;

import com.neko.malscraperanime.model.response.ReviewResponse;
import com.neko.malscraperanime.model.response.TopAnimeResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

public interface CsvFileGeneratorService {

    void writeTopAnimesToCsv(Set<TopAnimeResponse> topAnimeResponses, Writer writer) throws IOException;
    void writeReviewsToCsvWeb(List<ReviewResponse> reviews, Writer writer) throws IOException;

}
