package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.GenreDao;
import by.babanin.booklibrary.domain.Genre;
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
public class GenreController extends GeneralController<Genre> {
    private static final int DEFAULT_ROWS_COUNT = 20;
    private int rowsCount = DEFAULT_ROWS_COUNT;
    private int first;
    @Autowired
    private GenreDao genreDao;
    private SprController sprController;
    private Genre selectedGenre;
    private LazyDataModel<Genre> dataModel;
    private Page<Genre> genrePage;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Genre> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        if (Objects.isNull(sortField)) sortField = "name";
        String searchText = sprController.getSearchText();
        if (Objects.isNull(searchText) || searchText.isEmpty())
            genrePage = genreDao.getAll(pageNumber, count, sortField, sortDirection);
        else
            genrePage = genreDao.search(pageNumber, count, sortField, sortDirection, searchText);
        return genrePage;
    }

    @Override
    public void addAction() {
        selectedGenre = new Genre();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        genreDao.delete(selectedGenre);
    }

    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<Genre> find(String name) {
        return genreDao.search(name);
    }

    public void save() {
        genreDao.save(selectedGenre);
        hideEditDialog();
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').show()");
    }

    private void hideEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
    }
}
