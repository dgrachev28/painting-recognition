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
//        rest.get("http://ru.wikipedia.org/w/api.php?format=json&action=query&prop=pageimages&titles=Охотники на привале");

//        rest.get("http://ru.wikipedia.org/w/api.php?format=json&action=query&titles=Image:Wassilij_Grigorjewitsch_Perow_004.jpg&prop=imageinfo&iiprop=url");
        parser.parseSite();
    }
}
