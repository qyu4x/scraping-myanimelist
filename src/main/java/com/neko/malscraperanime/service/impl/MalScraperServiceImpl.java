package com.neko.malscraperanime.service.impl;

import com.neko.malscraperanime.config.MalScraperConfig;
import com.neko.malscraperanime.model.response.TopAnimeResponse;
import com.neko.malscraperanime.service.MalScraperService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class MalScraperServiceImpl implements MalScraperService {

    private MalScraperConfig malScraperConfig;

    @Autowired
    public MalScraperServiceImpl(MalScraperConfig malScraperConfig) {
        this.malScraperConfig = malScraperConfig;
    }

    @Override
    public Set<TopAnimeResponse> topAnime(Integer count) throws IOException {
        Set<TopAnimeResponse> topAnimeResponses = new HashSet<>();
        for (Integer page = 0; page <= count; page += 50) {
            String topAnimeUrl = malScraperConfig.getTopAnimeUrl().concat(String.valueOf(page));
            log.info("Fetching page {} with url {}", page, topAnimeUrl);

            Document document = Jsoup.connect(topAnimeUrl).get();
            Elements titles = document.getElementsByClass("ranking-list");

            titles.stream().forEach(title -> {
                String specificUrl = title.getElementsByClass("hoverinfo_trigger fl-l fs14 fw-b anime_ranking_h3").select("a[href]").attr("abs:href");
                log.info("Fetching specific {} ", specificUrl);

                try {
                    Document specificDocument = Jsoup.connect(specificUrl).get();
                    Elements wrappers = specificDocument.getElementsByClass("wrapper");

                    wrappers.stream().forEach(wrapper -> {
                        String japaneseTitle = wrapper.select("div[itemprop]").select("strong").text();
                        String englishTitle = wrapper.select("div[itemprop]").select("p").text();
                        String coverImageUrl = wrapper.select("div[style]").select("a").select("img").attr("abs:data-src");
                        String type = wrapper.select("span:contains(Type:)").get(0).parent().text();
                        String episodes = wrapper.select("span:contains(Episodes:)").get(0).parent().text();
                        String status = wrapper.select("span:contains(Status:)").get(0).parent().text();
                        String aired = wrapper.select("span:contains(Aired:)").get(0).parent().text();
                        String producers = wrapper.select("span:contains(Producers:)").get(0).parent().text();
                        String studios = wrapper.select("span:contains(Studios:)").get(0).parent().text();
                        String source = wrapper.select("span:contains(Source:)").get(0).parent().text();
                        String genres = wrapper.select("span[itemprop=genre]").get(0).parent().text();
                        String duration = wrapper.select("span:contains(Duration:)").get(0).parent().text();
                        String rating = wrapper.select("span:contains(Rating:)").get(0).parent().text();
                        String score = wrapper.select("span:contains(Score:)").get(0).parent().select("span[itemprop=ratingValue]").text();
                        String ratingCount = wrapper.select("span:contains(Score:)").get(0).parent().select("span[itemprop=ratingCount]").text();
                        String ranked = wrapper.select("span:contains(Ranked:)").get(0).parent().ownText() ;
                        String popularity = wrapper.select("span:contains(Popularity:)").get(0).parent().text();
                        String members = wrapper.select("span:contains(Members:)").get(0).parent().text();
                        String favorites = wrapper.select("span:contains(Favorites:)").get(0).parent().text();
                        String description = wrapper.select("p[itemprop=description]").text();


                        log.info("Japanese title {} ", japaneseTitle);
                        log.info("English title {} ", englishTitle);
                        log.info("Image url {} ", coverImageUrl);
                        log.info("Type {} ", type.substring("Type: ".length()-1));
                        log.info("Episode {} ", episodes.substring("Episodes: ".length()-1));
                        log.info("Status {} ", status.substring("Status: ".length()-1));
                        log.info("Aired {} ", aired.substring("Aired: ".length()-1));
                        log.info("Producers {} ", producers.substring("Producers: ".length()-1));
                        log.info("Studios {} ", studios.substring("Studios: ".length()-1));
                        log.info("Source {} ", source.substring("Source: ".length()-1));
                        log.info("Genre {} ", genres.substring("Genres: ".length()-1));
                        log.info("Duration {} ", duration.substring("Duration: ".length()-1));
                        log.info("Rating {} ", rating.substring("Rating: ".length()-1));
                        log.info("Score {} ", score);
                        log.info("Rating count {} ", ratingCount);
                        log.info("Ranked {} ", ranked.substring(1));
                        log.info("Popularity {} ", popularity.substring("Popularity: ".length()-1));
                        log.info("Member {} ", members.substring("Members: ".length()));
                        log.info("Favorites {} ", favorites.substring("Favorites: ".length()-1));
                        log.info("Description {}", description.substring(0, description.length () - "[Written by MAL Rewrite]".length()-1));

                        TopAnimeResponse topAnimeResponse = TopAnimeResponse.builder()
                                .japaneseTitle(japaneseTitle)
                                .englishTitle(englishTitle)
                                .coverImageUrl(coverImageUrl)
                                .type(type.substring("Type: ".length()-1))
                                .episodes(episodes.substring("Episodes: ".length()-1))
                                .status(status.substring("Status: ".length()-1))
                                .aired(aired.substring("Aired: ".length()-1))
                                .producers(producers.substring("Producers: ".length()-1))
                                .studios(studios.substring("Studios: ".length()-1))
                                .score(source.substring("Source: ".length()-1))
                                .genres(genres.substring("Genres: ".length()-1))
                                .duration(duration.substring("Duration: ".length()-1))
                                .rating(rating.substring("Rating: ".length()-1))
                                .score(score)
                                .ratingCount(ratingCount)
                                .ranked(ranked.substring(1))
                                .popularity(popularity.substring("Popularity: ".length()))
                                .members(members.substring("Members: ".length()-1))
                                .favorites(favorites.substring("Favorites: ".length()-1))
                                .description(description.substring(0, description.length () - "[Written by MAL Rewrite]".length()-1))
                                .build();

                        topAnimeResponses.add(topAnimeResponse);

                    });


                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

            });
            log.info("done");
        }
        return topAnimeResponses;
    }
}
