package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "READER")
public final class Reader {
    private int id;
    private String firstName;
    private String lastName;
    private Date accCreated;
    private List<Borrowing> borrowings = new ArrayList<>();

    public Reader() {
    }

    public Reader(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accCreated = new Date();
    }

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accCreated = new Date();
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public int getId() {
        return id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LASTNAME")
    public String getLastName() {
        return lastName;
    }

    @NotNull
    @Column(name = "ACC_CREATED")
    @Temporal(TemporalType.DATE)
    public Date getAccCreated() {
        return accCreated;
    }

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setAccCreated(Date accCreated) {
        this.accCreated = accCreated;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }
}