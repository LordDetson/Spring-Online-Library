package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.AuthorDao;
import by.babanin.booklibrary.domain.Author;
import by.babanin.booklibrary.web.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;
import java.util.Objects;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
public class AuthorController extends GeneralController<Author> {
    public static final int DEFAULT_PAGE_SIZE = 20;
    @Autowired
    private AuthorDao authorDao;
    private SprController sprController;
    private Author selectedAuthor;
    private int rowsCount = DEFAULT_PAGE_SIZE;
    private int first;
    private Page<Author> authorPage;
    private LazyDataModel<Author> dataModel;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Author> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        if (Objects.isNull(sortField)) sortField = "fio";
        String searchText = sprController.getSearchText();
        if (Objects.isNull(searchText) || searchText.isEmpty())
            authorPage = authorDao.getAll(pageNumber, count, sortField, sortDirection);
        else
            authorPage = authorDao.search(pageNumber, count, sortField, sortDirection, searchText);
        return authorPage;
    }

    @Override
    public void addAction() {
        selectedAuthor = new Author();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        authorDao.delete(selectedAuthor);
    }

    public List<Author> find(String fio) {
        return authorDao.search(fio);
    }

    public void save() {
        authorDao.save(selectedAuthor);
        hideEditDialog();
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').show()");
    }

    private void hideEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').hide()");
    }
}
