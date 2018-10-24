package com.crud.library.repository;

import com.crud.library.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface CopyDao extends CrudRepository<Copy, Integer> {
    @Override
    Copy save(Copy copy);
}