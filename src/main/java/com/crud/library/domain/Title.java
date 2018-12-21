package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Entity
@Access(AccessType.FIELD)
@Table(name = "TITLES")
public final class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @NotNull
    @Column(name = "PUBLISHED")
    private int published;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "title",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Copy> copies = new ArrayList<>();

    public Title(int id, String title, String author, int published) {
        this(title, author, published);
        this.id = id;
    }

    public Title(String title, String author, int published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }
}