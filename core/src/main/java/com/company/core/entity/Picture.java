package com.company.core.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title")
    private LocalizedString title = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "description")
    private LocalizedString description = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "year")
    private LocalizedString year = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "short_info")
    private LocalizedString shortInfo = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery")
    private LocalizedString gallery = new LocalizedString();

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalizedString getTitle() {
        return title;
    }

    public void setTitle(LocalizedString title) {
        this.title = title;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(LocalizedString description) {
        this.description = description;
    }

    public LocalizedString getYear() {
        return year;
    }

    public void setYear(LocalizedString year) {
        this.year = year;
    }

    public LocalizedString getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(LocalizedString shortInfo) {
        this.shortInfo = shortInfo;
    }

    public LocalizedString getGallery() {
        return gallery;
    }

    public void setGallery(LocalizedString gallery) {
        this.gallery = gallery;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
