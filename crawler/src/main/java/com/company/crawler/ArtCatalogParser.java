package com.company.crawler;

import com.company.core.AppProperties;
import com.company.core.entity.Author;
import com.company.core.entity.LocalizedString;
import com.company.core.entity.Picture;
import com.company.core.repository.PictureRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class ArtCatalogParser {

    private static final Logger log = LoggerFactory.getLogger(ArtCatalogParser.class);

    private static final int PICTURES_COUNT = 10;
    //    private static final int PICTURES_COUNT = 21065;
    private static final int PICTURES_ON_PAGE_COUNT = 90;
    private static final String DOMAIN = "http://www.art-catalog.ru";
    private static final String SITE_URL = DOMAIN + "/gallery.php";
    private static final String LOCALE = LocalizedString.RU;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AppProperties appProperties;

    public void parseSite() {
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
            picture.getYear().put(LOCALE, element.child(3).text());
        }
        picture.getTitle().put(LOCALE, element.child(2).text());
        String picturePageUrl = element.child(1).child(0).attr("href");
        parsePicturePage(picturePageUrl, picture);
        picture = pictureRepository.save(picture);

        String imageUrl = element.child(1).child(0).child(0).attr("src").replace("/thumb/", "/picture/");
        String imagePath = saveImageFile(imageUrl, picture);
        picture.setImagePath(imagePath);
        pictureRepository.save(picture);
    }

    private String saveImageFile(String imageUrl, Picture picture) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        String imagePath = appProperties.getImageFolder() + "/" + picture.getId() + ".jpg";
        ImageIO.write(image, "jpg", new File(imagePath));
        return imagePath;
    }


    private void parsePicturePage(String relativeUrl, Picture picture) throws IOException {
        Document doc = Jsoup.connect(DOMAIN + relativeUrl).get();
        Elements elements = doc.select(".page-content .row div");

        String shortInfo = elements.get(0).select("p").stream().map(Element::text).collect(Collectors.joining("\n"));
        String author = elements.select("a[href~=artist.php]").text();
        String gallery = elements.select("a[href~=gallery.php]").text();
        String authorLifeYears = elements.select("a[href~=artist.php]").parents().get(0).nextElementSibling().text();

        picture.getAuthor().getName().put(LOCALE, author);
        picture.getAuthor().getLifeYears().put(LOCALE, authorLifeYears);
        picture.getGallery().put(LOCALE, gallery);
        picture.getShortInfo().put(LOCALE, shortInfo);
    }
}
