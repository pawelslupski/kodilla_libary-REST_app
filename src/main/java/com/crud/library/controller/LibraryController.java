package com.crud.library.controller;

import com.crud.library.domain.*;
import com.crud.library.mapper.BorrowingMapper;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
public class LibraryController {
    @Autowired
    private DbService service;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private BorrowingMapper borrowingMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/readers", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        service.saveReader(readerMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/titles", consumes = APPLICATION_JSON_VALUE)
    public void createTitle(@RequestBody TitleDto titleDto) {
        service.saveTitle(titleMapper.mapToTitle(titleDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/copies", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto) {
        service.saveCopy(copyMapper.mapToCopy(copyDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/readers/{readerId}/borrow")
    public ReaderDto borrowTheCopy(@PathVariable int readerId,
                                   @RequestParam String copyTitle) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(service.borrowTheCopy(readerId, copyTitle)); }

    @RequestMapping(method = RequestMethod.PUT, value = "/copies/return/{copyId}")
    public BorrowingDto returnTheCopy(@PathVariable int copyId) {
        return borrowingMapper.mapToBorrowingDto(service.returnTheCopy(copyId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readers/{readerId}")
    public ReaderDto getReaderById(@PathVariable int readerId) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(service.getReaderById(readerId).orElseThrow(() ->
        new ReaderNotFoundException("There is no reader with this id in database")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/borrowings/{copyId}")
    public List<BorrowingDto> getBorrowingsByCopyId(@PathVariable int copyId) {
        return borrowingMapper.mapToBorrowingDtoList(service.getTheBorrowingsByCopyId(copyId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies")
    public List<CopyDto> getCopies() {
        return copyMapper.mapToCopyDtoList(service.getAllCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies/status")
    public List<CopyDto> getCopiesByStatus(@RequestParam String status) {
        return copyMapper.mapToCopyDtoList(service.getCopiesByStatus(status));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies/titles")
    public List<CopyDto> getAllCopiesBasedOnTitle(@RequestParam String title) {
        return copyMapper.mapToCopyDtoList(service.getAllCopiesWithTitle(title));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies/available/titles")
    public List<CopyDto> getAllAvailableCopiesBasedOnTitle(@RequestParam String title) {
        return copyMapper.mapToCopyDtoList(service.getAvailableCopiesWithTitle(title));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies/available/count")
    public long getNumberOfAvailableCopiesBasedOnTitle(@RequestParam String title) {
        return service.countAllAvailableCopiesWithTitle(title);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/copies/{copyId}")
    public CopyDto updateCopyStatus(@PathVariable int copyId,
                                    @RequestParam String status) {
        return copyMapper.mapToCopyDto(service.getCopyByIdAndChangeStatus(copyId, status));
    }
}


