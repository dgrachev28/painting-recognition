package com.company.crawler.component;

import com.company.core.entity.Author;
import com.company.core.entity.Picture;
import com.company.core.repository.PictureRepository;
import com.company.core.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ArtCatalogParser implements Parser {

    private static final int PICTURES_COUNT = 10;
    //    private static final int PICTURES_COUNT = 21065;
    private static final int PICTURES_ON_PAGE_COUNT = 90;
    private static final String DOMAIN = "http://www.art-catalog.ru";
    private static final String SITE_URL = DOMAIN + "/gallery.php";

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private FileService fileService;

    @Override
    public void parse() {
        for (int i = 0; i < PICTURES_COUNT; i += PICTURES_ON_PAGE_COUNT) {
            parseOnePage(i);
        }
    }

    private void parseOnePage(int start) {
        try {
            Document doc = Jsoup.connect(SITE_URL + "?start=" + start).get();
            Elements elements = doc.select(".picture-list td");
            for (Element element : elements) {
                try {
                    parseOnePicture(element);
                } catch (RuntimeException | IOException e) {
                    // catch RuntimeException because we can have NPE or OutOfArray while parsing and it's normal
                    // and we can't let one error fail whole parsing
                    log.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void parseOnePicture(Element element) throws IOException {
        Picture picture = new Picture();
        picture.setAuthor(new Author());
        if (element.children().size() > 3) {
            picture.setYear(element.child(3).text());
        }
        picture.setTitle(element.child(2).text());
        String picturePageUrl = element.child(1).child(0).attr("href");
        parsePicturePage(picturePageUrl, picture);
        picture = pictureRepository.save(picture); // we use picture id in file name later, so we need to save picture to get id

        String imageUrl = element.child(1).child(0).child(0).attr("src").replace("/thumb/", "/picture/");
        String imagePath = fileService.saveImageFile(imageUrl, picture.getId());
        picture.setImagePath(imagePath);
        pictureRepository.save(picture);
    }


    private void parsePicturePage(String relativeUrl, Picture picture) throws IOException {
        Document doc = Jsoup.connect(DOMAIN + relativeUrl).get();
        Elements elements = doc.select(".page-content .row div");

        String shortInfo = elements.get(0).select("p").stream().map(Element::text).collect(Collectors.joining("\n"));
        String author = elements.select("a[href~=artist.php]").text();
        String gallery = elements.select("a[href~=gallery.php]").text();
        String authorLifeYears = elements.select("a[href~=artist.php]").parents().get(0).nextElementSibling().text();

        picture.getAuthor().setName(author);
        picture.getAuthor().setLifeYears(authorLifeYears);
        picture.setGallery(gallery);
        picture.setShortInfo(shortInfo);
    }
}
