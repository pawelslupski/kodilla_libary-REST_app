package com.crud.library.repository;

import com.crud.library.domain.Copy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CopyDao extends CrudRepository<Copy, Integer> {
    @Override
    Copy save(Copy copy);

    List<Copy> findByStatus(String status);

    @Query(nativeQuery = true)
    List<Copy> retrieveAvailableCopiesWithTitle(@Param("ARG") String title);

    @Override
    List<Copy> findAll();
}