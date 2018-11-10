package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "BORROWING")
public final class Borrowing {
    private int id;
    private Date borrowDate;
    private Date returnDate;
    private Copy copy;
    private Reader reader;

    public Borrowing(int id, Copy copy, Reader reader) {
        this.id = id;
        this.borrowDate = new Date();
        this.copy = copy;
        this.reader = reader;
    }

    public Borrowing(Copy copy, Reader reader) {
        this.borrowDate = new Date();
        this.copy = copy;
        this.reader = reader;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    public int getId() {
        return id;
    }

    @NotNull
    @Column(name = "BORROW_DATE")
    @Temporal(TemporalType.DATE)
    public Date getBorrowDate() {
        return borrowDate;
    }

    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getReturnDate() {
        return returnDate;
    }

    @NotNull
    @OneToOne
    @JoinColumn(name = "COPY_ID")
    public Copy getCopy() {
        return copy;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "READER_ID")
    @JsonBackReference
    public Reader getReader() {
        return reader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}