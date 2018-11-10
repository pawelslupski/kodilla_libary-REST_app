package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private int id;
    private String firstName;
    private String lastName;
    private Date accCreated;
    private List<BorrowingDto> borrowings = new ArrayList<>();
}
