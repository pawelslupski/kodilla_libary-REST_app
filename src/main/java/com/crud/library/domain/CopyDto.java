package com.crud.library.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyDto {
    private int id;
    private String status;
    private TitleDto title;
}