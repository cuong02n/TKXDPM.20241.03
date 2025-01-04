package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

// Book Entity
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("BOOK")
public class Book extends Product {
    @Column(nullable = false)
    private String authors;

    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    private String language;
    private String genre;

    public enum CoverType {
        PAPERBACK, HARDCOVER
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        this.setCategory(ProductCategory.BOOK);
    }
}
