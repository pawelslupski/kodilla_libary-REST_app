package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Access(AccessType.FIELD)
@Table(name = "READER")
public final class Reader {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @NotNull
    @Column(name = "ACC_CREATED")
    @Temporal(TemporalType.DATE)
    private Date accCreated;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Borrowing> borrowings = new ArrayList<>();

    public Reader(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
        this.accCreated = new Date();
    }

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accCreated = new Date();
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }
}