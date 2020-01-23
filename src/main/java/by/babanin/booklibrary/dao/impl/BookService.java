package by.babanin.booklibrary.dao.impl;

import by.babanin.booklibrary.dao.BookDao;
import by.babanin.booklibrary.dao.repository.BookRepository;
import by.babanin.booklibrary.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookService implements BookDao {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findTopBooks(int limit) {
        return bookRepository.findTopBooks(PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "avgRating"))).getContent();
    }

    @Override
    public byte[] getContent(Long id) {
        return bookRepository.getContent(id);
    }

    @Override
    public Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, Long genreId) {
        return bookRepository.findByGenre(genreId, PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public void updateViewCount(long viewCount, Long id) {
        bookRepository.updateViewCount(viewCount, id);
    }

    @Override
    public void updateRating(long totalRating, long totalVoteCount, int avgRating, Long id) {
        bookRepository.updateRating(totalRating, totalVoteCount, avgRating, id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Page<Book> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return bookRepository.findAllWithoutContent(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<Book> search(String... patterns) {
        throw new UnsupportedOperationException("No method implementation");
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... patterns) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(patterns[0], patterns[1], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book save(Book instance) {
        Book book = bookRepository.save(instance);
        if (Objects.nonNull(instance.getContent())) {
            bookRepository.updateContent(instance.getContent(), instance.getId());
        }
        book.setContent(instance.getContent());
        return book;
    }

    @Override
    public void delete(Book instance) {
        bookRepository.delete(instance);
    }
}
