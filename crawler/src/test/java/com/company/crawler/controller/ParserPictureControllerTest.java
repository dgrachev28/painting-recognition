package com.company.crawler.controller;

import com.company.crawler.CrawlerApplication;
import com.company.crawler.model.ParserDto;
import com.company.core.entity.ParserType;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests are not ready yet, therefore they are ignored
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = H2)
@SpringBootTest(classes = CrawlerApplication.class)
@AutoConfigureMockMvc
public class ParserPictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Ignore
    @Test
    public void artCatalogTest() throws Exception {
        String request = new Gson().toJson(new ParserDto(ParserType.ART_CATALOG));
        this.mockMvc.perform(post("/parser/pictures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void wikipediaTest() throws Exception {
        String request = new Gson().toJson(new ParserDto(ParserType.WIKIPEDIA));
        this.mockMvc.perform(post("/parser/pictures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }
}
