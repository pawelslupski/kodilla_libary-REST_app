package com.crud.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NamedNativeQuery(
        name = "Copy.retrieveAvailableCopiesWithTitle",
        query = "SELECT * FROM COPY" +
                " WHERE STATUS LIKE 'Available' && TITLE_ID IN (SELECT TITLE_ID " +
                "FROM TITLES WHERE TITLE LIKE :ARG)",
        resultClass = Copy.class)
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "COPY")
public final class Copy {
    private int id;
    private String status;
    private Title title;

    public Copy() {
    }

    public Copy(String status, Title title) {
        this.status = status;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    public int getId() {
        return id;
    }

    @NotNull
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    public Title getTitle() {
        return title;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setTitle(Title title) {
        this.title = title;
    }
}