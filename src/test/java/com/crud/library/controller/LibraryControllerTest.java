package com.crud.library.controller;

import com.crud.library.domain.*;
import com.crud.library.mapper.BorrowingMapper;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private CopyMapper copyMapper;
    @MockBean
    private ReaderMapper readerMapper;
    @MockBean
    private TitleMapper titleMapper;
    @MockBean
    private BorrowingMapper borrowingMapper;

    @Test
    public void shouldCreateReader() throws Exception {
        //Given
        Reader reader = new Reader(
                45,
                "testName",
                "testLastname");
        ReaderDto readerDto = ReaderDto.builder()
                .firstName("testName")
                .lastName("testLastname")
                .build();

        Gson gson = new Gson();
        String jsonContent = gson.toJson(readerDto);

        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);

        //When&Then
        mockMvc.perform(post("/v1/library/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTitle() throws Exception {
        //Given
        Title title = new Title(
                21,
                "testTitle",
                "testAuthor",
                2000);
        TitleDto titleDto = new TitleDto(
                21,
                "testTitle",
                "testAuthor",
                2000);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(titleDto);

        when(titleMapper.mapToTitle(titleDto)).thenReturn(title);

        //When&Then
        mockMvc.perform(post("/v1/library/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateCopy() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 2);
        Copy copy = new Copy("TEST", title);
        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 2);
        CopyDto copyDto = new CopyDto(1, "TEST", titleDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyDto);

        when(copyMapper.mapToCopy(copyDto)).thenReturn(copy);

        //When&Then
        mockMvc.perform(post("/v1/library/copies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFetchCopies() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 2);
        Copy copy = new Copy("TEST", title);
        Copy copy2 = new Copy("TEST", title);
        List<Copy> copyList = new ArrayList<>();
        copyList.add(copy);
        copyList.add(copy2);

        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 2);
        CopyDto copyDto = new CopyDto(1, "TEST", titleDto);
        CopyDto copyDto2 = new CopyDto(1, "TEST", titleDto);
        List<CopyDto> copyDtoList = new ArrayList<>();
        copyDtoList.add(copyDto);
        copyDtoList.add(copyDto2);

        when(dbService.getAllCopies()).thenReturn(copyList);
        when(copyMapper.mapToCopyDtoList(copyList)).thenReturn(copyDtoList);

        //When&Then
        mockMvc.perform(get("/v1/library/copies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldFetchAllCopiesBasedOnTitle() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 1);
        Copy copy = new Copy("TEST", title);
        Copy copy2 = new Copy("TEST", title);
        List<Copy> copyList = new ArrayList<>();
        copyList.add(copy);
        copyList.add(copy2);

        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(1, "TEST", titleDto);
        CopyDto copyDto2 = new CopyDto(2, "TEST2", titleDto);
        List<CopyDto> copyDtoList = new ArrayList<>();
        copyDtoList.add(copyDto);
        copyDtoList.add(copyDto2);

        when(dbService.getAllCopiesWithTitle("testTitle")).thenReturn(copyList);
        when(copyMapper.mapToCopyDtoList(copyList)).thenReturn(copyDtoList);

        //When&Then
        String queryString = "?title=testTitle";
        mockMvc.perform(get("/v1/library/copies/titles" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("TEST")))
                .andExpect(jsonPath("$[0].title.title", is("testTitle")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is("TEST2")))
                .andExpect(jsonPath("$[1].title.title", is("testTitle")));
    }

    @Test
    public void shouldFetchAllAvailableCopiesBasedOnTitle() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 1);
        Copy copy = new Copy("AVAILABLE", title);
        List<Copy> copyList = new ArrayList<>();
        copyList.add(copy);

        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(1, "AVAILABLE", titleDto);
        List<CopyDto> copyDtoList = new ArrayList<>();
        copyDtoList.add(copyDto);

        when(dbService.getAvailableCopiesWithTitle("testTitle")).thenReturn(copyList);
        when(copyMapper.mapToCopyDtoList(copyList)).thenReturn(copyDtoList);

        //When&Then
        String queryString = "?title=testTitle";
        mockMvc.perform(get("/v1/library/copies/available/titles" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].title.title", is("testTitle")));
    }

    @Test
    public void shouldFetchAllCopiesByStatus() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 1);
        Copy copy = new Copy("TEST", title);
        List<Copy> copyList = new ArrayList<>();
        copyList.add(copy);

        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(333, "TEST", titleDto);
        List<CopyDto> copyDtoList = new ArrayList<>();
        copyDtoList.add(copyDto);

        when(dbService.getCopiesByStatus("TEST")).thenReturn(copyList);
        when(copyMapper.mapToCopyDtoList(copyList)).thenReturn(copyDtoList);

        //When&Then
        String queryString = "?status=TEST";
        mockMvc.perform(get("/v1/library/copies/status" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(333)))
                .andExpect(jsonPath("$[0].status", is("TEST")))
                .andExpect(jsonPath("$[0].title.title", is("testTitle")));
    }

    @Test
    public void shouldBorrowTheCopy() throws Exception {
        //Given
        Reader reader = new Reader(
                0,
                "testName",
                "testLastname");

        TitleDto titleDto = new TitleDto(5, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(55, "BORROWED", titleDto);
        BorrowingDto borrowingDto = new BorrowingDto(555, new Date(), null, copyDto);
        List<BorrowingDto> borrowingDtos = Arrays.asList(borrowingDto);

        ReaderDto readerDto = ReaderDto.builder()
                .id(0)
                .firstName("testName")
                .lastName("testLastname")
                .borrowings(borrowingDtos)
                .build();

        when(dbService.borrowTheCopy(1, "testTitle")).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);

        //When&Then
        String queryString = "?copyTitle=testTitle";

        mockMvc.perform(put("/v1/library/readers/1/borrow" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.firstName", is("testName")))
                .andExpect(jsonPath("$.lastName", is("testLastname")))
                .andExpect(jsonPath("$.borrowings[0].id", is(555)))
                .andExpect(jsonPath("$.borrowings[0].copy.id", is(55)))
                .andExpect(jsonPath("$.borrowings[0].copy.status", is("BORROWED")));
    }

    @Test
    public void shouldReturnTheCopy() throws Exception {
        //Given
        Reader reader = new Reader(
                1,
                "testName",
                "testLastname");
        Title title = new Title("testTitle", "testAuthor", 1);
        Copy copy = new Copy("TEST", title);
        Borrowing borrowing = new Borrowing(1, copy, reader);
        TitleDto titleDto = new TitleDto(5, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(55, "BORROWED", titleDto);
        BorrowingDto borrowingDto = new BorrowingDto(222, new Date(), new Date(), copyDto);

        when(dbService.returnTheCopy(1)).thenReturn(borrowing);
        when(borrowingMapper.mapToBorrowingDto(borrowing)).thenReturn(borrowingDto);

        //When&Then
        mockMvc.perform(put("/v1/library/copies/return/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(222)))
                .andExpect(jsonPath("$.borrowDate", is(notNullValue())))
                .andExpect(jsonPath("$.returnDate", is(notNullValue())))
                .andExpect(jsonPath("$.copy.id", is(55)));
    }

    @Test
    public void shouldUpdateCopyStatus() throws Exception {
        //Given
        Title title = new Title("testTitle", "testAuthor", 2000);
        Copy copy = new Copy(12, "TEST", title);

        TitleDto titleDto = new TitleDto(1, "testTitle", "testAuthor", 2000);
        CopyDto copyDto = new CopyDto(12, "AVAILABLE", titleDto);

        when(dbService.getCopyByIdAndChangeStatus(12, "Test")).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);

        //When&Then
        String queryString = "?status=Test";

        mockMvc.perform(put("/v1/library/copies/12" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.status", is("AVAILABLE")));
    }

    @Test
    public void shouldGetNumberOfAvailableCopiesBasedOnTitle() throws Exception {
        //Given
        when(dbService.countAllAvailableCopiesWithTitle("testTitle")).thenReturn(333L);

        //When&Then
        String queryString = "?title=testTitle";
        MvcResult result = mockMvc.perform(get("/v1/library/copies/available/count" + queryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.equals("333"));
    }

    @Test
    public void shouldGetReaderById() throws Exception {
        //Given
        Reader reader = new Reader(
                45,
                "testName",
                "testLastname");
        ReaderDto readerDto = ReaderDto.builder()
                .id(56)
                .firstName("testName")
                .lastName("testLastname")
                .build();

        when(dbService.getReaderById(45)).thenReturn(Optional.ofNullable(reader));
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);

        //When&Then
        mockMvc.perform(get("/v1/library/readers/45")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(56)))
                .andExpect(jsonPath("$.firstName", is("testName")))
                .andExpect(jsonPath("$.lastName", is("testLastname")));
    }

    @Test
    public void shouldFetchBorrowingsById() throws Exception {
        //Given
        Reader reader = new Reader(
                45,
                "testName",
                "testLastname");
        Title title = new Title("testTitle", "testAuthor", 1);
        Copy copy = new Copy("TEST", title);
        Borrowing borrowing = new Borrowing(1, copy, reader);
        List<Borrowing> borrowings = new ArrayList<>();
        borrowings.add(borrowing);

        ReaderDto readerDto = ReaderDto.builder()
                .id(56)
                .firstName("testName")
                .lastName("testLastname")
                .build();
        TitleDto titleDto = new TitleDto(5, "testTitle", "testAuthor", 1);
        CopyDto copyDto = new CopyDto(55, "BORROWED", titleDto);
        BorrowingDto borrowingDto = new BorrowingDto(222, new Date(), new Date(), copyDto);
        List<BorrowingDto> dtoBorrowings = new ArrayList<>();
        dtoBorrowings.add(borrowingDto);

        when(dbService.getTheBorrowingsByCopyId(1)).thenReturn(borrowings);
        when(borrowingMapper.mapToBorrowingDtoList(borrowings)).thenReturn(dtoBorrowings);

        //When&Then
        mockMvc.perform(get("/v1/library/borrowings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(222)))
                .andExpect(jsonPath("$[0].copy.id", is(55)))
                .andExpect(jsonPath("$[0].copy.status", is("BORROWED")))
                .andExpect(jsonPath("$[0].copy.title.title", is("testTitle")));
    }
}