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

    @RequestMapping(method = RequestMethod.POST, value = "createReader", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        service.saveReader(readerMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTitle", consumes = APPLICATION_JSON_VALUE)
    public void createTitle(@RequestBody TitleDto titleDto) {
        service.saveTitle(titleMapper.mapToTitle(titleDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCopy", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto) {
        service.saveCopy(copyMapper.mapToCopy(copyDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "borrowTheCopy")
    public ReaderDto borrowTheCopy(@RequestParam int readerId, String copyTitle) {
        return readerMapper.mapToReaderDto(service.borrowTheCopy(readerId, copyTitle));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "returnTheCopy")
    public BorrowingDto returnTheCopy(@RequestParam int copyId) {
        return borrowingMapper.mapToBorrowingDto(service.returnTheCopy(copyId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getReaderById")
    public ReaderDto getReaderById(@RequestParam int readerId) {
        return readerMapper.mapToReaderDto(service.getReaderById(readerId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBorrowingsByCopyId")
    public List<BorrowingDto> getBorrowingsByCopyId(@RequestParam int copyId) {
        return borrowingMapper.mapToBorrowingDtoList(service.getTheBorrowingsByCopyId(copyId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCopies")
    public List<CopyDto> getCopies() {
        return copyMapper.mapToCopyDtoList(service.getAllCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCopiesByStatus")
    public List<CopyDto> getCopiesByTitleAndStatus(@RequestParam String status) {
        return copyMapper.mapToCopyDtoList(service.getCopiesByStatus(status));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllCopiesBasedOnTitle")
    public List<CopyDto> getAllCopiesBasedOnTitle(@RequestParam String title) {
        return copyMapper.mapToCopyDtoList(service.getAllCopiesWithTitle(title));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAvailableCopiesBasedOnTitle")
    public List<CopyDto> getAllAvailableCopiesBasedOnTitle(@RequestParam String title) {
        return copyMapper.mapToCopyDtoList(service.getAvailableCopiesWithTitle(title));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getNumberOfAvailableCopiesBasedOnTitle")
    public long getNumberOfAvailableCopiesBasedOnTitle(@RequestParam String title) {
        return service.countAllAvailableCopiesWithTitle(title);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateCopyStatus")
    public CopyDto updateCopyStatus(@RequestParam int id, String status) {
        return copyMapper.mapToCopyDto(service.getCopyByIdAndChangeStatus(id, status));
    }
}


