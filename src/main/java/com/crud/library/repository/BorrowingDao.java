package com.crud.library.repository;

import com.crud.library.domain.Borrowing;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowingDao extends CrudRepository<Borrowing, Integer> {
    @Override
    Borrowing save(Borrowing borrowing);

    List<Borrowing> findByCopy_Id(int copyId);
}
