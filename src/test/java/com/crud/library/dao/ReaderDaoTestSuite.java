package com.crud.library.dao;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.repository.ReaderDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReaderDaoTestSuite {
    @Autowired
    private ReaderDao readerDao;

    private static final String STATUS_BORROWED = "Borrowed";

    @Test
    public void testReaderDaoSave() {
        //Given
        Reader reader = new Reader("Pawel", "Slupski");

        //When
        readerDao.save(reader);

        //Then
        int id = reader.getId();
        Reader readReader = readerDao.findOne(id);
        Assert.assertEquals(id, readReader.getId());

        //Cleanup
        readerDao.delete(id);
    }

    @Test
    public void testReaderDaoSaveWithBorrowingsAndCopies() {
        //Given
        Reader reader = new Reader("Pawel", "Slupski");
        Borrowing borrowing = new Borrowing(LocalDate.now());
        borrowing.setCopy(new Copy(STATUS_BORROWED));
        reader.getBorrowings().add(borrowing);
        borrowing.setReader(reader);

        //When
        readerDao.save(reader);
        int id = reader.getId();

        //Then
        Assert.assertNotEquals(0, id);

        //Cleanup
        readerDao.delete(id);
    }

}
