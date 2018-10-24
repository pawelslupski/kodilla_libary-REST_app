package com.crud.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "BORROWING")
public final class Borrowing {
    private int id;
    @Temporal(TemporalType.DATE)
    private LocalDate borrowDate;
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;
    private Copy copy;
    private Reader reader;

    public Borrowing() {
    }

    public Borrowing(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public int getId() {
        return id;
    }

    @NotNull
    @Column(name = "BORROW_DATE")
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    @Column(name = "RETURN_DATE")
    public LocalDate getReturnDate() {
        return returnDate;
    }

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COPY_ID")
    public Copy getCopy() {
        return copy;
    }

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    public Reader getReader() {
        return reader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}