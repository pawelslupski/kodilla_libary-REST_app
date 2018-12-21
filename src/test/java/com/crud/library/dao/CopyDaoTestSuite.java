package com.crud.library.dao;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.repository.CopyDao;
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
public class CopyDaoTestSuite {
    public static final String AVAILABLE = "AVAILABLE";
    public static final String BORROWED = "BORROWED";
    public static final String TITLE = "Misery";

    @Autowired
    CopyDao copyDao;
    @Autowired
    TitleDao titleDao;

    @Test
    public void testCopyDaoSave() {
        //Given
        Title title =  new Title("On the Niemen", "E.Orzeszkowa", 1888);
        Copy copy = new Copy(AVAILABLE, title);

        //When
        titleDao.save(title);
        copyDao.save(copy);

        //Then
        int id = copy.getId();
        int id2 = title.getId();
        Copy readCopy = copyDao.findOne(id);
        assertNotNull(readCopy);
        assertEquals(id, readCopy.getId());

        //Cleanup
        copyDao.delete(id);
        titleDao.delete(id2);
    }

    @Test
    public void testCopyDaoFindById() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);

        //When
        titleDao.save(title);
        copyDao.save(copy);

        //Then
        int id = copy.getId();
        int id2 = title.getId();
        Copy readCopy = copyDao.findById(id);
        assertNotNull(readCopy);
        assertEquals(id, readCopy.getId());

        //Cleanup
        titleDao.delete(id2);
    }

    @Test
    public void testCopyDaoFindByStatus() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);
        titleDao.save(title);
        copyDao.save(copy);

        //When
        List<Copy> readCopies = copyDao.findByStatus(Status.BORROWED);

        //Then
        assertEquals(1, readCopies.size());

        //Cleanup
        int id = title.getId();
        titleDao.delete(id);
    }

    @Test
    public void testCopyDaoRetrieveCopiesWithTitle() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);
        Copy copy2 = new Copy(AVAILABLE, title);
        titleDao.save(title);
        copyDao.save(copy);
        copyDao.save(copy2);

        //When
        List<Copy> readCopies = copyDao.retrieveAllCopiesWithTitle(TITLE);

        //Then
        assertEquals(2, readCopies.size());

        //Cleanup
        int id = title.getId();
        titleDao.delete(id);
    }

    @Test
    public void testCopyDaoRetrieveAvailableCopiesWithTitle() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Copy copy = new Copy(BORROWED, title);
        Copy copy2 = new Copy(AVAILABLE, title);
        titleDao.save(title);
        copyDao.save(copy);
        copyDao.save(copy2);

        //When
        List<Copy> readCopies = copyDao.retrieveAvailableCopiesWithTitle(TITLE);

        //Then
        assertEquals(1, readCopies.size());

        //Cleanup
        int id = title.getId();
        titleDao.delete(id);
    }

    @Test
    public void testCopyDaoFindAll() {
        //Given
        Title title =  new Title("Misery", "S.King", 1987);
        Title title2 =  new Title("On the Niemen", "E.Orzeszkowa", 1888);
        Copy copy = new Copy(BORROWED, title);
        Copy copy2 = new Copy(BORROWED, title2);
        Copy copy3 = new Copy(AVAILABLE, title2);
        titleDao.save(title);
        titleDao.save(title2);
        copyDao.save(copy);
        copyDao.save(copy2);
        copyDao.save(copy3);

        //When
        List<Copy> readCopies = copyDao.findAll();

        //Then
        assertEquals(3, readCopies.size());

        //Cleanup
        int id = title.getId();
        int id2 = title2.getId();
        titleDao.delete(id);
        titleDao.delete(id2);
    }
}
