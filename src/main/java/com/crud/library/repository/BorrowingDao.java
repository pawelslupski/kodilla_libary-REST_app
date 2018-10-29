package com.crud.library.repository;

import com.crud.library.domain.Borrowing;
import com.crud.library.domain.Copy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowingDao extends CrudRepository<Borrowing, Integer> {
    @Override
    Borrowing save(Borrowing borrowing);

    void deleteById(Long id);

    List<Borrowing> findByCopy_Id(int copyId);
}
