package com.crud.library.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleDto {
    private int id;
    private String title;
    private String author;
    private int published;
}
