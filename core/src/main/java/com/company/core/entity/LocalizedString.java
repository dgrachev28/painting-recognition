package com.company.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocalizedString {

    public static final String RU = "ru";
    public static final String EN = "en";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text_ru")
    private String textRu;
    @Column(name = "text_en")
    private String textEn;


    public String get(String locale) {
        switch (locale) {
            case RU:
                return getTextRu();
            case EN:
                return getTextEn();
            default:
                return getTextRu();
        }
    }

    public void put(String locale, String value) {
        switch (locale) {
            case RU:
                setTextRu(value);
                break;
            case EN:
                setTextEn(value);
                break;
            default:
                setTextRu(value);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextRu() {
        return textRu;
    }

    public void setTextRu(String textRu) {
        this.textRu = textRu;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }
}
