package com.plb.vinylmgt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "vinyl")
public class Vinyl {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Song name must not be null")
    @Column(name = "song_name", nullable = false)
    private String songName;

    @Column(name = "release_date")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.nnnZ")
    private LocalDate releaseDate;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    private User user;

    @ManyToOne
    private Author author;

    public Vinyl() {
    }

    public Vinyl(String songName, LocalDate releaseDate,
                 Author author, User user, String imageUrl) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.user = user;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Objects.equals(id, vinyl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", releaseDate=" + releaseDate +
                ", user=" + user +
                '}';
    }
}
