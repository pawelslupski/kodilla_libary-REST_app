package com.crud.library.domain;

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
    @Temporal(TemporalType.DATE)
    private Date accCreated;
    private List<Borrowing> borrowings = new ArrayList<>();

    public Reader() {
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
    public Date getAccCreated() {
        return accCreated;
    }

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
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

    private void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }
}