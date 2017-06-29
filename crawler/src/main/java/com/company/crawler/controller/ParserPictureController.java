package com.company.crawler.controller;

import com.company.crawler.component.Parser;
import com.company.crawler.model.ParserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/parser/pictures")
public class ParserPictureController {

    private final Map<String, Parser> parsers;

    public ParserPictureController(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    @PostMapping
    public void parsePictures(@RequestBody ParserDto dto) {
        parsers.get(dto.getParserType().getBeanName()).parse();
    }
}
