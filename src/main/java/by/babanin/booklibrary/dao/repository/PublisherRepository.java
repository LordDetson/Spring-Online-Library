package by.babanin.booklibrary.dao.repository;

import by.babanin.booklibrary.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Page<Publisher> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
