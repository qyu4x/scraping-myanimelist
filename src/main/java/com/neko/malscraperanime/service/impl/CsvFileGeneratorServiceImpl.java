package com.neko.malscraperanime.service.impl;

import com.neko.malscraperanime.model.response.ReviewResponse;
import com.neko.malscraperanime.model.response.TopAnimeResponse;
import com.neko.malscraperanime.service.CsvFileGeneratorService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

@Service
public class CsvFileGeneratorServiceImpl implements CsvFileGeneratorService {

    @Override
    public void writeTopAnimesToCsv(Set<TopAnimeResponse> topAnimeResponses, Writer writer) throws IOException {
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        topAnimeResponses.stream().forEach(topAnimeResponse -> {
            try {
                printer.printRecord(topAnimeResponse.getJapaneseTitle(), topAnimeResponse.getEnglishTitle(), topAnimeResponse.getCoverImageUrl(),
                        topAnimeResponse.getType(), topAnimeResponse.getEpisodes(), topAnimeResponse.getStatus(), topAnimeResponse.getAired(),
                        topAnimeResponse.getProducers(), topAnimeResponse.getStudios(), topAnimeResponse.getSource(), topAnimeResponse.getGenres(),
                        topAnimeResponse.getDuration(), topAnimeResponse.getRating(), topAnimeResponse.getSource(), topAnimeResponse.getRatingCount(),
                        topAnimeResponse.getRanked(), topAnimeResponse.getPopularity(), topAnimeResponse.getMembers(), topAnimeResponse.getFavorites(),
                        topAnimeResponse.getDescription());
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        });
        printer.close();
    }

    @Override
    public void writeReviewsToCsvWeb(List<ReviewResponse> reviews, Writer writer) throws IOException {
        String[] headers = {"Username", "Anime Title", "Comment", "Label", "Created At"};

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
        reviews.stream().forEach(review -> {
            try  {
                csvPrinter.printRecord(review.getUsername(), review.getAnimeTitle(), review.getComment(), review.getTags(), review.getReviewedAt());
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        });

        csvPrinter.close();
    }
}
