package com.company.core.entity;

public enum ParserType {

    ART_CATALOG("artCatalogParser"),
    WIKIPEDIA("wikipediaParser");

    private String beanName;

    ParserType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
