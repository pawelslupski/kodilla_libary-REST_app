package com.crud.library.mapper;

import com.crud.library.domain.Copy;
import com.crud.library.domain.CopyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CopyMapper {
    @Autowired
    TitleMapper titleMapper;

    public Copy mapToCopy(final CopyDto copyDto) {
        return new Copy(copyDto.getStatus(), titleMapper.mapToTitle(copyDto.getTitle()));
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(copy.getStatus(),(titleMapper.mapToTitleDto(copy.getTitle())));
    }

}
