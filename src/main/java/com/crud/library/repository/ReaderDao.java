package com.crud.library.repository;

import com.crud.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ReaderDao extends CrudRepository<Reader, Integer> {
    @Override
    Reader save(Reader reader);

    Reader findById(int id);
}
