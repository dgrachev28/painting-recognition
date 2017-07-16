package com.company.core.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class PictureDescriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picture_descriptor_id_seq")
    @SequenceGenerator(name = "picture_descriptor_id_seq", sequenceName = "picture_descriptor_id_seq")
    private Long id;

    @NotNull
    private String descriptor;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Picture picture;

}
