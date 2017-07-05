package com.company.crawler.component;

import com.company.core.entity.Author;
import com.company.core.entity.ParserType;
import com.company.core.entity.Picture;
import com.company.core.repository.AuthorRepository;
import com.company.core.repository.PictureRepository;
import com.company.core.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

    private final PictureRepository pictureRepository;
    private final AuthorRepository authorRepository;
    private final FileService fileService;

    public ArtCatalogParser(PictureRepository pictureRepository, AuthorRepository authorRepository,
                            FileService fileService) {
        this.pictureRepository = pictureRepository;
        this.authorRepository = authorRepository;
        this.fileService = fileService;
    }

    @Override
    public void parse() {
        for (int i = 0; i < PICTURES_COUNT; i += PICTURES_ON_PAGE_COUNT) {
            parseOnePage(i);
        }
    }

    private void parseOnePage(int start) {
        try {
            Document doc = Jsoup.connect(SITE_URL + "?start=" + start).get();
            doc.select(".picture-list td").forEach(this::parseOnePicture);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void parseOnePicture(Element element) {
        try {
            doParseOnePicture(element);
        } catch (RuntimeException | IOException e) {
            // catch RuntimeException because we can have NPE or OutOfArray while parsing and it's normal
            // and we can't let one error fail whole parsing
            log.error(e.getMessage(), e);
        }
    }

    private void doParseOnePicture(Element element) throws IOException {
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
        fillPictureFields(picture, elements, relativeUrl);
        picture.setAuthor(getOrCreateAuthor(elements));
    }

    private void fillPictureFields(Picture picture, Elements elements, String relativeUrl) {
        picture.setGallery(elements.select("a[href~=gallery.php]").text());
        picture.setShortInfo(elements.get(0).select("p").stream()
                .map(Element::text)
                .collect(Collectors.joining("\n")));
        picture.setReference(DOMAIN + relativeUrl);
        picture.setParserType(ParserType.ART_CATALOG);
    }

    private Author getOrCreateAuthor(Elements elements) {
        Elements authorElement = elements.select("a[href~=artist.php]");

        Author author = authorRepository.findByReference(authorElement.attr("href"));
        if (author == null) {
            author = new Author();
            author.setName(authorElement.text());
            author.setReference(authorElement.attr("href"));
            author.setLifeYears(authorElement.parents().get(0).nextElementSibling().text());
            author.setParserType(ParserType.ART_CATALOG);
        }
        return author;
    }
}
