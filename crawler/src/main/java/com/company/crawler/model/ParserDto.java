package com.company.crawler.model;


import com.company.core.entity.ParserType;
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
