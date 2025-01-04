package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DVD extends Product {
    @Enumerated(EnumType.STRING)
    @Column(name = "disc_type")
    private DiscType discType;

    private String director;

    private Integer runtime;  // in minutes

    private String studio;

    private String language;

    private String subtitles;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String genre;

    public enum DiscType {
        BLURAY, HD_DVD
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        this.setCategory(ProductCategory.DVD);
    }
}

