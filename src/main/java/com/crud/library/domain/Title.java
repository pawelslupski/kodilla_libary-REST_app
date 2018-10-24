package com.crud.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "TITLES")
public final class Title {
    private int id;
    private String title;
    private String author;
    private int published;
    private List<Copy> copies = new ArrayList<>();

    public Title() {
    }

    public Title(int id, String title, String author, int published) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public Title(String title, String author, int published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    public int getId() {
        return id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    @Column(name = "AUTHOR")
    public String getAuthor() {
        return author;
    }

    @NotNull
    @Column(name = "PUBLISHED")
    public int getPublished() {
        return published;
    }

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "title",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Copy> getCopies() {
        return copies;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setPublished(int published) {
        this.published = published;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }
}