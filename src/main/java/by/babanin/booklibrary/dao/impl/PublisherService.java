package by.babanin.booklibrary.dao.impl;

import by.babanin.booklibrary.dao.PublisherDao;
import by.babanin.booklibrary.dao.repository.PublisherRepository;
import by.babanin.booklibrary.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PublisherService implements PublisherDao {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    @Override
    public List<Publisher> getAll(Sort sort) {
        return publisherRepository.findAll(sort);
    }

    @Override
    public Page<Publisher> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return publisherRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<Publisher> search(String... patterns) {
        throw new UnsupportedOperationException("No method implementation");
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... patterns) {
        return publisherRepository.findByNameContainingIgnoreCaseOrderByName(patterns[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public Publisher getById(Long id) {
        return publisherRepository.getOne(id);
    }

    @Override
    public Publisher save(Publisher instance) {
        return publisherRepository.save(instance);
    }

    @Override
    public void delete(Publisher instance) {
        publisherRepository.delete(instance);
    }
}
