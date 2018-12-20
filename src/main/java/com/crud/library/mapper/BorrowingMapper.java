package com.crud.library.mapper;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.BorrowingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowingMapper {
    @Autowired
    CopyMapper copyMapper;
    @Autowired
    ReaderMapper readerMapper;

    public Borrowing mapToBorrowing(final BorrowingDto borrowingDto) {
        return new Borrowing(borrowingDto.getId(), copyMapper.mapToCopy(borrowingDto.getCopy()),
                readerMapper.mapToReader(borrowingDto.getReader()));
    }

    public BorrowingDto mapToBorrowingDto(final Borrowing borrowing) {
        return new BorrowingDto(borrowing.getId(), borrowing.getBorrowDate(), borrowing.getReturnDate(),
                copyMapper.mapToCopyDto(borrowing.getCopy()));
    }

    public List<BorrowingDto> mapToBorrowingDtoList(final List<Borrowing> borrowingList) {
        return borrowingList.stream()
                .map(b -> new BorrowingDto(b.getId(), b.getBorrowDate(), b.getReturnDate(),
                        copyMapper.mapToCopyDto(b.getCopy())))
                .collect(Collectors.toList());
    }
}
