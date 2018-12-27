package com.crud.library.repository;

import com.crud.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface TitleDao extends CrudRepository<Title, Integer> {
    @Override
    Title save(Title title);

    @Override
    long count();
}