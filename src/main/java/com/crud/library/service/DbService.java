package com.crud.library.service;

import com.crud.library.config.AdminConfig;
import com.crud.library.controller.ReaderNotFoundException;
import com.crud.library.domain.*;
import com.crud.library.repository.BorrowingDao;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;

@Service
public class DbService {
    private static final String MAIL_SUBJECT_READER_ADDED = "Library: New Reader";
    private static final String MAIL_SUBJECT_TITLE_ADDED = "Library: New Title";
    private static final String MAIL_SUBJECT_COPY_ADDED = "Library: New Copy";

    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private TitleDao titleDao;
    @Autowired
    private CopyDao copyDao;
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    private AdminConfig adminConfig;

    public Reader saveReader(final Reader reader) {
        Reader newReader = readerDao.save(reader);
        ofNullable(newReader).ifPresent(r -> emailService.send(new Mail(adminConfig.getAdminMail(),
                MAIL_SUBJECT_READER_ADDED, "New Reader: [id:" + r.getId() + ", firstname: " +
                r.getFirstName() + ", lastname: " + r.getLastName() + "] has been added to the " +
                "database")));
        return newReader;
    }

    public Optional<Reader> getReaderById(int readerId) {
        return readerDao.findById(readerId);
    }

    public Title saveTitle(final Title title) {
        Title newTitle = titleDao.save(title);
        ofNullable(newTitle).ifPresent(t -> emailService.send(new Mail(adminConfig.getAdminMail(),
                MAIL_SUBJECT_TITLE_ADDED, "New Title: [id:" + t.getId() + ", title: " +
                t.getTitle() + ", author: " + t.getAuthor() + "] has been added to the database")));
        return newTitle;
    }

    public Copy saveCopy(final Copy copy) {
        Copy newCopy = copyDao.save(copy);
        ofNullable(newCopy).ifPresent(c -> emailService.send(new Mail(adminConfig.getAdminMail(),
                MAIL_SUBJECT_COPY_ADDED, "New Copy: [id:" + c.getId() + ", title: " + c.getTitle().getTitle() +
                "] has been added to the database")));
        return newCopy;
    }

    public List<Copy> getAllCopies() {
        return copyDao.findAll();
    }

    public List<Copy> getCopiesByStatus(String status) {
        return copyDao.findByStatus(Status.valueOf(status.toUpperCase()));
    }

    public List<Copy> getAvailableCopiesWithTitle(String title) {
        return copyDao.retrieveAvailableCopiesWithTitle(title);
    }

    public List<Copy> getAllCopiesWithTitle(String title) {
        return copyDao.retrieveAllCopiesWithTitle(title);
    }

    public long countAllAvailableCopiesWithTitle(String title) {
        List<Copy> listOfAllAvailableCopies = copyDao.findByStatus(Status.AVAILABLE);
        return listOfAllAvailableCopies.stream()
                .map(c -> c.getTitle())
                .filter(t -> t.getTitle().equals(title))
                .count();
    }

    public Copy getCopyByIdAndChangeStatus(int id, String status) {
        Copy copy = copyDao.findById(id);
        copy.setStatus(Status.valueOf(status.toUpperCase()));
        copyDao.save(copy);
        return copy;
    }

    public Reader borrowTheCopy(int readerId, String searchTitle) throws ReaderNotFoundException {
        Optional<Reader> optionalReader = readerDao.findById(readerId);
        optionalReader.orElseThrow(() ->
                new ReaderNotFoundException("There is no reader with this id in database"));
        Reader reader = optionalReader.get();
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
