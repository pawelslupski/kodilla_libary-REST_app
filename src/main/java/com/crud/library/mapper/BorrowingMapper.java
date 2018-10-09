package com.crud.library.mapper;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.BorrowingDto;
import org.springframework.stereotype.Component;

@Component
public class BorrowingMapper {
    public Borrowing mapToBorrowing(final BorrowingDto borrowingDto) {
        return new Borrowing(borrowingDto.getBorrowDate());
    }

    public BorrowingDto mapToBorrowingDto(final Borrowing borrowing) {
        return new BorrowingDto(borrowing.getBorrowDate());
    }

}
