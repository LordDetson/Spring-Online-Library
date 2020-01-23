package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.BookDao;
import by.babanin.booklibrary.domain.Book;
import by.babanin.booklibrary.web.enums.SearchType;
import by.babanin.booklibrary.web.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
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
@Log
public class BookController extends GeneralController<Book> {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_TOP_SIZE = 5;
    @Autowired
    private BookDao bookDao;
    private int rowsCount = DEFAULT_PAGE_SIZE;
    private SearchType searchType;
    private Page<Book> bookPage;
    private List<Book> topBooks;
    private LazyDataModel<Book> dataModel;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Book> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        if (Objects.isNull(sortField)) sortField = "name";
        if (Objects.isNull(searchType)) {
            bookPage = bookDao.getAll(pageNumber, count, sortField, sortDirection);
        } else {
            switch (searchType) {
                case ALL:
                    bookPage = bookDao.getAll(pageNumber, count, sortField, sortDirection);
                    break;
                case SEARCH_TEXT:
                    break;
                case SEARCH_GENRE:
                    break;
            }
        }
        return bookPage;
    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(DEFAULT_TOP_SIZE);
        return topBooks;
    }
}
