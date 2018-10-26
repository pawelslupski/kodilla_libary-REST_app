package com.crud.library.dao;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Title;
import com.crud.library.repository.TitleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TitleDaoTestSuite {
    @Autowired
    private TitleDao titleDao;

    public static final String AVAILABLE = "AVAILABLE";
    private static final String BORROWED = "BORROWED";
    private static final String LOST = "LOST";

    @Test
    public void testTitleDaoSave() {
        //Given
        Title title = new Title("On the Niemen", "E.Orzeszkowa", 1888);

        //When
        titleDao.save(title);

        //Then
        int id = title.getId();
        Title readTitle = titleDao.findOne(id);
        Assert.assertEquals(id, readTitle.getId());

        //Cleanup
        titleDao.delete(id);
    }

    @Test
    public void testTitleDaoSaveWithCopies() {
        //Given
        Title title1 = new Title("On the Niemen", "E.Orzeszkowa", 1888);
        Title title2 = new Title("The doll", "B.Prus", 1890);
        Copy copy1 = new Copy(BORROWED, title1);
        Copy copy2 = new Copy(BORROWED, title1);
        Copy copy3 = new Copy(AVAILABLE, title2);
        Copy copy4 = new Copy(LOST, title2);
        title1.getCopies().add(copy1);
        title1.getCopies().add(copy2);
        title2.getCopies().add(copy3);
        title2.getCopies().add(copy4);
        copy1.setTitle(title1);
        copy2.setTitle(title1);
        copy3.setTitle(title2);
        copy4.setTitle(title1);

        //When
        titleDao.save(title1);
        titleDao.save(title2);
        int id = title1.getId();
        int id2 = title2.getId();

        //Then
        Assert.assertNotEquals(0, id);
        Assert.assertNotEquals(0, id2);

        //Cleanup
        titleDao.delete(id);
        titleDao.delete(id2);
    }
}
