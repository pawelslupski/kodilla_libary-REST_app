package com.crud.library.mapper;

import com.crud.library.domain.Reader;
import com.crud.library.domain.ReaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {
    @Autowired
    BorrowingMapper borrowingMapper;

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(readerDto.getId(), readerDto.getFirstName(), readerDto.getLastName());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(reader.getId(), reader.getFirstName(), reader.getLastName(),
                reader.getAccCreated(), borrowingMapper.mapToBorrowingDtoList(reader.getBorrowings()));
    }
}
