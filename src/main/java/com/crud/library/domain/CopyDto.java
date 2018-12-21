package com.crud.library.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class CopyDto {
    private int id;
    private String status;
    private TitleDto title;
}