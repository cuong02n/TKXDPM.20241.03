package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("CD")
public class CD extends Product {
    private String artists;

    @Column(name = "record_label")
    private String recordLabel;

    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ElementCollection
    @CollectionTable(
            name = "cd_tracklist",
            joinColumns = @JoinColumn(name = "cd_id")
    )
    @OrderColumn(name = "track_number")
    private List<Track> tracklist;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        this.setCategory(ProductCategory.CD);
    }

    @Data
    @Embeddable
    public static class Track {
        @Column(name = "track_name", nullable = false)
        private String name;

        private String duration;
    }
}