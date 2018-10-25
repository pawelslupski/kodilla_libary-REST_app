package com.crud.library.mapper;

import com.crud.library.domain.Copy;
import com.crud.library.domain.CopyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {
    @Autowired
    TitleMapper titleMapper;

    public Copy mapToCopy(final CopyDto copyDto) {
        return new Copy(copyDto.getId(), copyDto.getStatus(),
                titleMapper.mapToTitle(copyDto.getTitle()));
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(copy.getId(), copy.getStatus(),
                titleMapper.mapToTitleDto(copy.getTitle()));
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copyList) {
        return copyList.stream()
                .map(c -> new CopyDto(c.getId(), c.getStatus(), titleMapper.mapToTitleDto(c.getTitle())))
                .collect(Collectors.toList());
    }
}
