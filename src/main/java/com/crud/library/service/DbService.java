package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.repository.BorrowingDao;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DbService {
    public final static String AVAILABLE = "Available";
    public final static String BORROWED = "Borrowed";
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private TitleDao titleDao;
    @Autowired
    private CopyDao copyDao;
    @Autowired
    private BorrowingDao borrowingDao;

    public Reader saveReader(final Reader reader) {
        return readerDao.save(reader);
    }

    public Reader getReaderById(int readerId) {
        return readerDao.findById(readerId);
    }

    public Borrowing saveBorrowing(final Borrowing borrowing) {
        return borrowingDao.save(borrowing);
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

    public Copy getCopyByIdAndChangeStatus(int id, String status) {
        Copy copy = copyDao.findById(id);
        copy.setStatus(Status.valueOf(status));
        copyDao.save(copy);
        return copy;
    }

    public Reader borrowTheCopy(int readerId, String searchTitle) {
        Reader reader = readerDao.findById(readerId);
        List<Copy> availableCopiesWithTitle = copyDao.retrieveAvailableCopiesWithTitle(searchTitle);
        if (availableCopiesWithTitle.size() > 0) {
            Copy theCopy = availableCopiesWithTitle.get(0);
            theCopy.setStatus(Status.BORROWED);
            Borrowing borrowing = new Borrowing(theCopy, reader);
            borrowing.setReader(reader);
            reader.getBorrowings().add(borrowing);
            readerDao.save(reader);
            return reader;
        }
        return reader;
    }

    public List<Borrowing> getTheBorrowingsByCopyId(int copyId) {
        return borrowingDao.findByCopy_Id(copyId); }

    public Borrowing returnTheCopy(int copyId) {
        List<Borrowing> listOfBorrowing = borrowingDao.findByCopy_Id(copyId);
        List<Borrowing> currentlyNotReturnedBorrowing = listOfBorrowing.stream()
                .filter(borrowing -> borrowing.getReturnDate() == null)
                .collect(Collectors.toList());
        if(currentlyNotReturnedBorrowing.size() != 1) {
            throw new IllegalStateException();
        }
        Borrowing borrowing = currentlyNotReturnedBorrowing.get(0);
        borrowing.setReturnDate(new Date());
        borrowing.getCopy().setStatus(Status.AVAILABLE);
        Reader reader = borrowing.getReader();
        reader.getBorrowings().remove(borrowing);
        readerDao.save(reader);

        return borrowing;
    }
}
