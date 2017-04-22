package com.company.crawler;

import com.company.core.CoreApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class CrawlerTest {

    @Autowired
    private Crawler crawler;

    @Test
    public void test() {
        crawler.search();
    }
}
