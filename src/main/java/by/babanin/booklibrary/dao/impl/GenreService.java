package by.babanin.booklibrary.dao.impl;

import by.babanin.booklibrary.dao.GenreDao;
import by.babanin.booklibrary.dao.repository.GenreRepository;
import by.babanin.booklibrary.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GenreService implements GenreDao {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> getAll(Sort sort) {
        return genreRepository.findAll(sort);
    }

    @Override
    public Page<Genre> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        throw new UnsupportedOperationException("No method implementation");
    }

    @Override
    public List<Genre> search(String... patterns) {
        return genreRepository.findByNameContainingIgnoreCaseOrderByName(patterns[0]);
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... patterns) {
        throw new UnsupportedOperationException("No method implementation");
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.getOne(id);
    }

    @Override
    public Genre save(Genre instance) {
        return genreRepository.save(instance);
    }

    @Override
    public void delete(Genre instance) {
        genreRepository.delete(instance);
    }
}
