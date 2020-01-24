package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.AuthorDao;
import by.babanin.booklibrary.domain.Author;
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
public class AuthorController extends GeneralController<Author> {
    @Autowired
    private AuthorDao authorDao;
    private Page<Author> authorPage;
    private LazyDataModel<Author> dataModel;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Author> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        throw new UnsupportedOperationException("No method implementation");
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

    public List<Author> find(String fio) {
        return authorDao.search(fio);
    }
}
