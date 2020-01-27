package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.BookDao;
import by.babanin.booklibrary.dao.GenreDao;
import by.babanin.booklibrary.domain.Book;
import by.babanin.booklibrary.web.enums.SearchType;
import by.babanin.booklibrary.web.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

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
    @Autowired
    private GenreDao genreDao;
    private int rowsCount = DEFAULT_PAGE_SIZE;
    private SearchType searchType = SearchType.ALL;
    private Page<Book> bookPage;
    private List<Book> topBooks;
    private LazyDataModel<Book> dataModel;
    private Long selectedGenreId;
    private String searchText;
    private byte[] uploadedImage;
    private byte[] uploadedContent;
    private Book selectedBook;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Book> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        if (Objects.isNull(sortField)) sortField = "name";
        switch (searchType) {
            case ALL:
                bookPage = bookDao.getAll(pageNumber, count, sortField, sortDirection);
                break;
            case SEARCH_TEXT:
                bookPage = bookDao.search(pageNumber, count, sortField, sortDirection, searchText, searchText);
                break;
            case SEARCH_GENRE:
                bookPage = bookDao.findByGenre(pageNumber, count, sortField, sortDirection, selectedGenreId);
                bookPage.getContent().forEach(System.out::println);
                break;
        }
        return bookPage;
    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(DEFAULT_TOP_SIZE);
        return topBooks;
    }

    public void setAll() {
        searchType = SearchType.ALL;
        this.selectedGenreId = null;
    }

    public void searchByGenre(Long id) {
        searchType = SearchType.SEARCH_GENRE;
        this.selectedGenreId = id;
    }

    public void searchByBookName() {
        searchType = SearchType.SEARCH_TEXT;
    }

    public String getSearchMessage() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("book_library", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String message = null;
        switch (searchType) {
            case SEARCH_GENRE:
                message = resourceBundle.getString("genre") + ": '" + genreDao.getById(selectedGenreId).getName() + "'";
                break;
            case SEARCH_TEXT:
                message = resourceBundle.getString("search") + ": '" + searchText + "'";
                break;
        }
        return message;
    }

    public void uploadImage(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (Objects.nonNull(file))
            uploadedImage = file.getContents();
    }

    public void uploadContent(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (Objects.nonNull(file))
            uploadedContent = file.getContents();
    }

    public void save() {
        if (Objects.nonNull(uploadedImage)) selectedBook.setImage(uploadedImage);
        if (Objects.nonNull(uploadedContent)) selectedBook.setContent(uploadedContent);
        bookDao.save(selectedBook);
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");
    }

    public void onCloseDialog(CloseEvent event) {
        uploadedImage = null;
        uploadedContent = null;
    }

    @Override
    public void addAction() {
        selectedBook = new Book();
        uploadedImage = loadDefaultImage();
        uploadedContent = null;

        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    private byte[] loadDefaultImage() {
        InputStream imageStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/no-cover.jpg");
        try {
            return IOUtils.toByteArray(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editAction() {
        uploadedImage = selectedBook.getImage();
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    @Override
    public void deleteAction() {
        bookDao.delete(selectedBook);
    }

    public void onrate(RateEvent event) {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int bookIndex = Integer.parseInt(requestParameterMap.get("bookIndex"));
        Book book = bookPage.getContent().get(bookIndex);
        long rating = Long.parseLong(event.getRating().toString());
        long newRating = book.getTotalRating() + rating;
        long newVoteCount = book.getTotalVoteCount() + 1;
        int newAvgRating = calcAverageRating(newRating, newVoteCount);

        System.out.println("avgRating = " + newAvgRating + ", totalVoteCount = " + newVoteCount + ", totalRating = " + newRating);
        bookDao.updateRating(newRating, newVoteCount, newAvgRating, book.getId());
    }

    private int calcAverageRating(long totalRating, long totalVoteCount) {
        if (totalRating == 0 || totalVoteCount == 0)
            return 0;
        return Long.valueOf(totalRating / totalVoteCount).intValue();
    }
}