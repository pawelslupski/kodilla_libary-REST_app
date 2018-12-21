package com.crud.library.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class TitleDto {
    private int id;
    private String title;
    private String author;
    private int published;
}
