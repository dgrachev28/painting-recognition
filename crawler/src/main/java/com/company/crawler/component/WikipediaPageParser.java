package com.company.crawler.component;

import com.company.core.entity.Picture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Component
public class WikipediaPageParser {

    private static final String BASE_URL = "https://ru.wikipedia.org/wiki/";

    public void parseOnePage(String title, Picture picture) {
        try {
            Document doc = Jsoup.connect(BASE_URL + URLEncoder.encode(title, "UTF-8").replaceAll("\\+", "%20")).get();
            Elements elements = doc.select(".infobox>tbody>tr");
//            elements.get(0).select("img").
            String author = elements.get(1).text();
            String pictureTitle = elements.get(2).select("b").text();
            String year = elements.get(2).select("span").text();
            String shortInfo = elements.get(4).text();
            String gallery = elements.get(5).text();

            picture.getAuthor().setName(author);
            picture.setTitle(pictureTitle);
            picture.setYear(year);
            picture.setShortInfo(shortInfo);
            picture.setGallery(gallery);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
