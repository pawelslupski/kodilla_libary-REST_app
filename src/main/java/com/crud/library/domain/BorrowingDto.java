package com.crud.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
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
