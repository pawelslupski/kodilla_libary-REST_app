package com.crud.library.repository;

import com.crud.library.domain.Borrowing;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowingDao extends CrudRepository<Borrowing, Integer> {
    @Override
    Borrowing save(Borrowing borrowing);

    void deleteById(Long id);

}
