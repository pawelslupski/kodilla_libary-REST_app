package com.crud.library.dao;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Title;
import com.crud.library.repository.BorrowingDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BorrowingDaoTestSuite {
    public static final String BORROWED = "BORROWED";

    @Autowired
    BorrowingDao borrowingDao;
    @Autowired
    ReaderDao readerDao;
    @Autowired
    TitleDao titleDao;

    @Test
    public void testBorrowingDaoSave() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);
        title.getCopies().add(copy);
        Reader reader = new Reader("Barbara", "Streisand");
        Borrowing borrowing = new Borrowing(copy, reader);
        //When
        titleDao.save(title);
        readerDao.save(reader);
        borrowingDao.save(borrowing);
        //Then
        int id = borrowing.getId();
        int id2 = title.getId();
        int id3 = reader.getId();
        Borrowing readBorrowing = borrowingDao.findOne(id);
        assertNotNull(readBorrowing);
        assertEquals(id, readBorrowing.getId());

        //Cleanup
        borrowingDao.delete(id);
        titleDao.delete(id2);
        readerDao.delete(id3);
    }

    @Test
    public void testBorrowingFindByCopyId() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);
        title.getCopies().add(copy);
        Reader reader = new Reader("Barbara", "Streisand");
        Borrowing borrowing = new Borrowing(copy, reader);
        //When
        titleDao.save(title);
        readerDao.save(reader);
        borrowingDao.save(borrowing);
        //Then
        int id = title.getId();
        int id2 = borrowing.getId();
        int id3 = reader.getId();
        List<Borrowing> readBorrowings = borrowingDao.findByCopy_Id(id);
        assertEquals(1, readBorrowings.size());

        //Cleanup
        borrowingDao.delete(id2);
        titleDao.delete(id);
        readerDao.delete(id3);
    }
}
