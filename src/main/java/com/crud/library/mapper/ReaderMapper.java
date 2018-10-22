package com.crud.library.mapper;

import com.crud.library.domain.Reader;
import com.crud.library.domain.ReaderDto;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(readerDto.getFirstName(), readerDto.getLastName());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(reader.getFirstName(), reader.getLastName());
    }
}
