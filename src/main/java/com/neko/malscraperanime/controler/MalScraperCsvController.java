package com.neko.malscraperanime.controler;

import com.neko.malscraperanime.service.CsvFileGeneratorService;
import com.neko.malscraperanime.service.MalScraperService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/csv")
public class MalScraperCsvController {

    private final MalScraperService malScraperService;

    private final CsvFileGeneratorService csvFileGeneratorService;

    @Autowired
    public MalScraperCsvController(MalScraperService malScraperService, CsvFileGeneratorService csvFileGeneratorService) {
        this.malScraperService = malScraperService;
        this.csvFileGeneratorService = csvFileGeneratorService;
    }

    @GetMapping("/top-anime")
    public void topAnime(@RequestParam("limit") Integer limit, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=top-anime.csv");
        csvFileGeneratorService.writeTopAnimesToCsv(malScraperService.topAnime(limit), response.getWriter());
    }
}
