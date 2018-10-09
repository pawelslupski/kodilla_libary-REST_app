package com.crud.library.mapper;

import com.crud.library.domain.Copy;
import com.crud.library.domain.CopyDto;
import org.springframework.stereotype.Component;

@Component
public class CopyMapper {
    public Copy mapToCopy(final CopyDto copyDto) {
        return new Copy(copyDto.getStatus());
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(copy.getStatus());
    }

}
