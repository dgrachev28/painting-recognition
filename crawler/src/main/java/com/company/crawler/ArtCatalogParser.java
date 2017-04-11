package com.company.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ArtCatalogParser {

    private static final int PICTURES_COUNT = 21065;
    private static final int PICTURES_ON_PAGE_COUNT = 90;
    private static final String SITE_URL = "http://www.art-catalog.ru/gallery.php";


    public void parseSite() {
        for (int i = 0; i < PICTURES_COUNT; i += PICTURES_ON_PAGE_COUNT) {
            parseOnePage(i);

            break;
        }
    }

    private void parseOnePage(int start) {
        try {
            Document doc = Jsoup.connect(SITE_URL + "?start=" + start).get();
            Elements elements = doc.select(".picture-list td");
            for (Element element : elements) {
                String author = element.child(0).text();
                String pictureUrl = element.child(1).child(0).child(0).attr("src").replace("/thumb/", "/picture/");
                String pictureTitle = element.child(2).text();

                URL url = new URL(pictureUrl);
                BufferedImage image = ImageIO.read(url);
                ImageIO.write(image, "jpg", new File(author + "_" + pictureTitle + ".jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
