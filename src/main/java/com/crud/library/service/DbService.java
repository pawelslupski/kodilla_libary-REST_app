package com.crud.library.service;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Title;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private TitleDao titleDao;
    @Autowired
    private CopyDao copyDao;

    public Reader saveReader(final Reader reader) {
        return readerDao.save(reader);
    }

    public Title saveTitle(final Title title) {
        return titleDao.save(title);
    }

    public Copy saveCopy(final Copy copy) {
        return copyDao.save(copy);
    }

}
