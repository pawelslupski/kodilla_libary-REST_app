package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Access(AccessType.FIELD)
@Table(name = "BORROWING")
public final class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    private int id;

    @NotNull
    @Column(name = "BORROW_DATE")
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy")
    private Date returnDate;

    @NotNull
    @OneToOne
    @JoinColumn(name = "COPY_ID")
    private Copy copy;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "READER_ID")
    @JsonBackReference
    private Reader reader;

    public Borrowing(int id, Copy copy, Reader reader) {
        this(copy, reader);
        this.id = id;
        this.borrowDate = new Date();
    }

    public Borrowing(Copy copy, Reader reader) {
        this.borrowDate = new Date();
        this.copy = copy;
        this.reader = reader;
    }
}