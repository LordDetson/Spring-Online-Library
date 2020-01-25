package by.babanin.booklibrary.dao.repository;

import by.babanin.booklibrary.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByFioContainingIgnoreCase(String fio, Pageable pageable);

    List<Author> findByFioContainingIgnoreCaseOrderByFio(String fio);
}
