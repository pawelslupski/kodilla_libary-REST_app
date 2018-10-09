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

    public CopyDto(String status) {
        this.status = status;
    }

}