package com.neko.malscraperanime.service;

import com.neko.malscraperanime.model.response.TopAnimeResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public interface CsvFileGeneratorService {

    public void writeTopAnimesToCsv(Set<TopAnimeResponse> topAnimeResponses, Writer writer) throws IOException;

}
