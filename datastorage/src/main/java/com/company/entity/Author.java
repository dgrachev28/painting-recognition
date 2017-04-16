package com.company.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name")
    private LocalizedString name = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "description")
    private LocalizedString description = new LocalizedString();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "life_years")
    private LocalizedString lifeYears = new LocalizedString();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalizedString getName() {
        return name;
    }

    public void setName(LocalizedString name) {
        this.name = name;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(LocalizedString description) {
        this.description = description;
    }

    public LocalizedString getLifeYears() {
        return lifeYears;
    }

    public void setLifeYears(LocalizedString lifeYears) {
        this.lifeYears = lifeYears;
    }
}
