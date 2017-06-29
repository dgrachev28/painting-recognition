package com.company.crawler.model;


import lombok.Data;

@Data
public class ParserDto {

    private ParserType parserType;

    public ParserDto() {
    }

    public ParserDto(ParserType parserType) {
        this.parserType = parserType;
    }
}
