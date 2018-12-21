package com.crud.library.dao;

import com.crud.library.domain.Title;
import com.crud.library.repository.TitleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TitleDaoTestSuite {
    @Autowired
    private TitleDao titleDao;

    @Test
    public void testTitleDaoSave() {
        //Given
        Title title = new Title("On the Niemen", "E.Orzeszkowa", 1888);

        //When
        titleDao.save(title);

        //Then
        int id = title.getId();
        Title readTitle = titleDao.findOne(id);
        assertNotNull(readTitle);
        assertEquals(id, readTitle.getId());

        //Cleanup
        titleDao.delete(id);
    }
}
