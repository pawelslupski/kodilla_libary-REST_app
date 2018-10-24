package com.crud.library.controller;

import com.crud.library.domain.Copy;
import com.crud.library.domain.CopyDto;
import com.crud.library.domain.ReaderDto;
import com.crud.library.domain.TitleDto;
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

    /*@RequestMapping(method = RequestMethod.PUT, value = "updateCopyStatus")
    public CopyDto updateCopyStatus(@RequestBody CopyDto copyDto) {
        return copyMapper.mapToCopyDto(service.saveCopy(copyMapper.mapToCopy(copyDto)));
    }*/
}

