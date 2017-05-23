package com.company.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class WikipediaParser {

    private static final String DOMAIN = "https://ru.wikipedia.org";
    private static final String SITE_URL = DOMAIN + "/w/index.php?title=Категория:Картины_по_алфавиту";

    public void parsePictures(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".mw-category li a");
            for (Element element : elements) {
                try {
                    parseOnePicture(element.attr("title"));
                } catch (RuntimeException e) {
                    // catch RuntimeException because we can have NPE or OutOfArray while parsing and it's normal
                    // and we can't let one error fail whole parsing
                    log.error(e.getMessage(), e);
                }
            }
            getNextPageUrl(doc).ifPresent(this::parsePictures);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Optional<String> getNextPageUrl(Document doc) {
        Elements elements = doc.select("#mw-pages>a");
        return elements.stream()
                .filter(e -> Objects.equals(e.text(), "Следующая страница"))
                .findAny()
                .map(e -> DOMAIN + e.attr("href"));
    }

    // TODO: сделать обработку каждой картины
    private void parseOnePicture(String title) {
        log.info(title);
    }

    // TODO: написать тест
    public static void main(String[] args) {
        new WikipediaParser().parsePictures(SITE_URL);
    }
}
