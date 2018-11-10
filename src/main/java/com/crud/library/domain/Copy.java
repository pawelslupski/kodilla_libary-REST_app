package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Copy.retrieveAvailableCopiesWithTitle",
                query = "SELECT * FROM COPY " +
                        "WHERE STATUS LIKE 'Available' && TITLE_ID IN (SELECT TITLE_ID " +
                        "FROM TITLES WHERE TITLE LIKE :ARG)",
                resultClass = Copy.class),
        @NamedNativeQuery(
                name = "Copy.retrieveAllCopiesWithTitle",
                query = "SELECT * FROM COPY " +
                        "WHERE TITLE_ID IN (SELECT TITLE_ID " +
                        "FROM TITLES WHERE TITLE LIKE :ARG)",
                resultClass = Copy.class),
})
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "COPY")
public final class Copy {
    private int id;
    private Status status;
    private Title title;

    public Copy() {
    }

    public Copy(int id, String status, Title title) {
        this.id = id;
        this.status = Status.valueOf(status);
        this.title = title;
    }

    public Copy(String status, Title title) {
        this.status = Status.valueOf(status);
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
    @Column(name = "STATUS", columnDefinition = "enum('AVAILABLE','BORROWED','LOST')")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    @JsonBackReference
    public Title getTitle() {
        return title;
    }

    private void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}