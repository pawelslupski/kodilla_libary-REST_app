package com.crud.library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BorrowingDto {
    private int id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private CopyDto copy;
    private ReaderDto reader;

    public BorrowingDto(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

}
