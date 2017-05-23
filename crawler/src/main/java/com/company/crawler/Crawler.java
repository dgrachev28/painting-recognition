package com.company.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Crawler {

    @Autowired
    private RestClient rest;
    @Autowired
    private ArtCatalogParser parser;

    public void search() {
        rest.get("http://ru.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext=&titles=Охотники_на_привале");
//        parser.parseSite();
    }
}
