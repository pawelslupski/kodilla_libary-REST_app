package com.crud.library.dao;

import com.crud.library.domain.Copy;
import com.crud.library.repository.CopyDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CopyDaoTestSuite {
    @Autowired
    private CopyDao copyDao;

    private static final String STATUS_BORROWED = "Borrowed";
    private static final String STATUS_AVAILABLE = "Available";
    private static final String STATUS_DESTROYED = "Destroyed";
    private static final String STATUS_LOST = "Lost";

    @Test
    public void testCopyDaoSave() {
        //Given
        Copy copy = new Copy(STATUS_AVAILABLE);
        Copy copy2 = new Copy(STATUS_BORROWED);
        Copy copy3 = new Copy(STATUS_LOST);
        Copy copy4 = new Copy(STATUS_DESTROYED);

        //When
        copyDao.save(copy);
        copyDao.save(copy2);
        copyDao.save(copy3);
        copyDao.save(copy4);
        int id = copy.getId();
        int id2 = copy2.getId();
        int id3 = copy3.getId();
        int id4 = copy4.getId();

        //Then
        Assert.assertNotEquals(0, id);
        Assert.assertNotEquals(0, id2);
        Assert.assertNotEquals(0, id3);
        Assert.assertNotEquals(0, id4);

        //Cleanup
        copyDao.delete(id);
        copyDao.delete(id2);
        copyDao.delete(id3);
        copyDao.delete(id4);
    }

    @Test
    public void testCopyDaoUpdateTheCopyStatusWhereTheIdIs() {
        //do nothing yet
    }

}
