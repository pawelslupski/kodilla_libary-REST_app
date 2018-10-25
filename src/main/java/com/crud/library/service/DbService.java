package com.crud.library.service;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Title;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
    public final static String AVAILABLE = "Available";
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

    public List<Copy> getAllCopies() {
        return copyDao.findAll();
    }

    public List<Copy> getCopiesByStatus(String status) {
        return copyDao.findByStatus(status);
    }

    public List<Copy> getAvailableCopiesWithTitle(String title) {
        return copyDao.retrieveAvailableCopiesWithTitle(title);
    }

    public List<Copy> getAllCopiesWithTitle(String title) {
        return copyDao.retrieveAllCopiesWithTitle(title);
    }

    public long countAllAvailableCopiesWithTitle(String title) {
        List<Copy> listOfAllAvailableCopies = copyDao.findByStatus(AVAILABLE);
        return listOfAllAvailableCopies.stream()
                .map(c -> c.getTitle())
                .filter(t -> t.getTitle().equals(title))
                .count();
    }

    public Copy getCopyByIdAndAlterStatus(int id, String status) {
        Copy copy = copyDao.findById(id);
        copy.setStatus(status);
        copyDao.save(copy);
        return copy;
    }
}
