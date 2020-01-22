package by.babanin.booklibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAll();

    List<T> getAll(Sort sort);

    Page<T> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);

    List<T> search(String... patterns);

    Page<T> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... patterns);

    T getById(Long id);

    T save(T instance);

    void delete(T instance);
}
