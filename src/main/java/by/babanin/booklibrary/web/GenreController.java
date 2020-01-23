package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.GenreDao;
import by.babanin.booklibrary.domain.Genre;
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
public class GenreController extends GeneralController<Genre> {
    private static final int DEFAULT_ROWS_COUNT = 20;
    private int rowsCount = DEFAULT_ROWS_COUNT;
    @Autowired
    private GenreDao genreDao;
    private Genre selectedGenre;
    private LazyDataModel<Genre> dataModel;
    private Page<Genre> genrePage;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Genre> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        throw new UnsupportedOperationException("No method implementation");
    }

    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
