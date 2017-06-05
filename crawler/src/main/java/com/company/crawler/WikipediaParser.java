package com.company.crawler;

import com.company.core.entity.Author;
import com.company.core.entity.Picture;
import com.company.core.repository.PictureRepository;
import com.company.core.service.FileService;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class WikipediaParser {

    private static final String DOMAIN = "https://ru.wikipedia.org";
    public static final String SITE_URL = DOMAIN + "/w/index.php?title=Категория:Картины_по_алфавиту";
    private static final String API_URL = "http://ru.wikipedia.org/w/api.php";
    private static final String API_BASE_PARAMS_URL = API_URL + "?format=json&action=query";

    private final RestClient rest;
    private final WikipediaPageParser wikipediaPageParser;
    private final PictureRepository pictureRepository;
    private final FileService fileService;

    public WikipediaParser(RestClient rest, WikipediaPageParser wikipediaPageParser,
                           PictureRepository pictureRepository, FileService fileService) {
        this.rest = rest;
        this.wikipediaPageParser = wikipediaPageParser;
        this.pictureRepository = pictureRepository;
        this.fileService = fileService;
    }

    public void parsePictures(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".mw-category li a");
            for (Element element : elements) {
                try {
                    parseOnePicture(element.attr("title"));
                } catch (RuntimeException | IOException e) {
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

    private void parseOnePicture(String title) throws IOException {
        log.info(title);
        String pictureImageName = findPictureImageName(title);

        Picture picture = new Picture();
        picture.setAuthor(new Author());
        wikipediaPageParser.parseOnePage(title, picture);
        fileService.saveImageFile(findPictureImageUrl(pictureImageName), picture);
        pictureRepository.save(picture);
    }

    private String findPictureImageName(String title) throws UnsupportedEncodingException {
        String json = rest.get(API_BASE_PARAMS_URL + "&prop=pageimages&titles=" + URLEncoder.encode(title, "UTF-8"));
        List<String> strings = JsonPath.read(json, "$..pageimage");
        Assert.isTrue(strings.size() == 1, "findPictureImageName: Size must be equal 1");
        return strings.get(0);
    }

    private String findPictureImageUrl(String pictureName) throws UnsupportedEncodingException {
        String json = rest.get(API_BASE_PARAMS_URL + "&titles=Image:" + URLEncoder.encode(pictureName, "UTF-8") + "&prop=imageinfo&iiprop=url");
        List<String> strings = JsonPath.read(json, "$..url");
        Assert.isTrue(strings.size() == 1, "findPictureImageUrl: Size must be equal 1");
        return strings.get(0);
    }
}
