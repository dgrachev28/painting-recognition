package com.company.crawler;


public class Crawler {

    RestClient rest = new RestClient();
    ArtCatalogParser parser = new ArtCatalogParser();

    public void search() {
//        rest.get("http://ru.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext=&titles=Охотники_на_привале");
//            String s = rest.get("http://www.art-catalog.ru/gallery.php");
        parser.parseSite();
    }
}
