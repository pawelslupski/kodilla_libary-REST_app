package com.crud.library.mapper;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import org.springframework.stereotype.Component;

@Component
public class TitleMapper {
    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(titleDto.getTitle(), titleDto.getAuthor(), titleDto.getPublished());
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(title.getTitle(), title.getAuthor(), title.getPublished());
    }

}
