package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingDto {
    private int id;
    private Date borrowDate;
    private Date returnDate;
    private CopyDto copy;
    @JsonIgnore
    private ReaderDto reader;

    public BorrowingDto(int id, Date borrowDate, Date returnDate, CopyDto copy) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.copy = copy;
    }
}
