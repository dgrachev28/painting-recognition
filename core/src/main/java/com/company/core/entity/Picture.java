package com.company.core.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picture_id_seq")
    @SequenceGenerator(name = "picture_id_seq", sequenceName = "picture_id_seq")
    private Long id;

    @NotNull
    private String title;
    private String description;
    private String year;
    private String shortInfo;
    private String gallery;
    private String imagePath;

    @NotNull
    private String reference;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ParserType parserType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language = Language.RU;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn
    private Author author;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
