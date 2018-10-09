package com.crud.library.dao;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.Copy;
import com.crud.library.repository.BorrowingDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowingDaoTestSuite {
    @Autowired
    private BorrowingDao borrowingDao;

    private static final String STATUS_BORROWED = "Borrowed";

    @Test
    public void testBorrowingDaoSaveWithCopy() {
        //Given
        Borrowing borrowing = new Borrowing(LocalDate.now());
        borrowing.setCopy(new Copy(STATUS_BORROWED));

        //When
        borrowingDao.save(borrowing);
        int id = borrowing.getId();

        //Then
        Assert.assertNotEquals(0, id);

        //Cleanup
        borrowingDao.delete(id);
    }

}
