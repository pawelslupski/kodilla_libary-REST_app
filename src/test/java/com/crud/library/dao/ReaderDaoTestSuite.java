package com.crud.library.dao;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Title;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReaderDaoTestSuite {
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private CopyDao copyDao;
    @Autowired
    private TitleDao titleDao;

    public static final String AVAILABLE = "AVAILABLE";
    private static final String BORROWED = "BORROWED";
    private static final String LOST = "LOST";

    @Test
    public void testReaderDaoSave() {
        //Given
        Reader reader = new Reader("Barbara", "Streisand");

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
        Reader reader = new Reader("Brian", "Riley");
        Title title = new Title("On the Niemen",
                "E.Orzeszkowa", 1888);
        Copy copy = new Copy(BORROWED, title);
        Borrowing borrowing = new Borrowing(copy, reader);
        reader.getBorrowings().add(borrowing);
        borrowing.setReader(reader);

        //When
        titleDao.save(title);
        copyDao.save(copy);
        readerDao.save(reader);
        int id = reader.getId();

        //Then
        Assert.assertNotEquals(0, id);

        //Cleanup
        readerDao.delete(id);
    }
}
