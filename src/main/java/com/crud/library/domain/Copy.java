package com.crud.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
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
    @GeneratedValue
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

    public void setTitle(Title title) {
        this.title = title;
    }

}