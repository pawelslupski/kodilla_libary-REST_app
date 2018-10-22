package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TitleDto {
    private String title;
    private String author;
    private int published;
    private List<CopyDto> copies = new ArrayList<>();

    public TitleDto(String title, String author, int published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }
}
