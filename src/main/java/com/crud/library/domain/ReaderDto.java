package com.crud.library.domain;

import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class ReaderDto {
    private int id;
    private String firstName;
    private String lastName;
    private Date accCreated;
    private List<BorrowingDto> borrowings = new ArrayList<>();
}
