package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@Entity
@Access(AccessType.FIELD)
@Table(name = "COPY")
public final class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    private int id;

    @NotNull
    @Column(name = "STATUS", columnDefinition = "enum('AVAILABLE','BORROWED','LOST')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    @JsonBackReference
    private Title title;

    public Copy(int id, String status, Title title) {
        this(status, title);
        this.id = id;
    }

    public Copy(String status, Title title) {
        this.status = Status.valueOf(status.toUpperCase());
        this.title = title;
    }
}