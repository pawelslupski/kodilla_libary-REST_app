package com.crud.library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CopyDto {
    private int id;
    private String status;
    private TitleDto title;

    public CopyDto(String status, TitleDto title) {
        this.status = status;
        this.title = title;
    }
}