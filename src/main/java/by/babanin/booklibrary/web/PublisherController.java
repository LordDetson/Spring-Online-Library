package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.PublisherDao;
import by.babanin.booklibrary.domain.Publisher;
import by.babanin.booklibrary.web.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
public class PublisherController extends GeneralController<Publisher> {
    @Autowired
    private PublisherDao publisherDao;
    private Page<Publisher> publisherPage;
    private LazyDataModel<Publisher> dataModel;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Publisher> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        return null;
    }

    @Override
    public void addAction() {

    }

    @Override
    public void editAction() {

    }

    @Override
    public void deleteAction() {

    }

    public List<Publisher> find(String name) {
        return publisherDao.search(name);
    }
}
