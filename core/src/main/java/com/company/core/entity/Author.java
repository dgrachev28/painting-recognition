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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq")
    private Long id;

    @NotNull
    private String name;
    private String description;
    private String lifeYears;

    @NotNull
    private String reference;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ParserType parserType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language = Language.RU;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
