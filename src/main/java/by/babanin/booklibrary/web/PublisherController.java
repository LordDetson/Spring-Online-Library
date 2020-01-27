package by.babanin.booklibrary.web;

import by.babanin.booklibrary.dao.PublisherDao;
import by.babanin.booklibrary.domain.Publisher;
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
public class PublisherController extends GeneralController<Publisher> {
    public static final int DEFAULT_PAGE_SIZE = 20;
    @Autowired
    private PublisherDao publisherDao;
    private SprController sprController;
    private Publisher selectedPublisher;
    private int rowsCount = DEFAULT_PAGE_SIZE;
    private int first;
    private Page<Publisher> publisherPage;
    private LazyDataModel<Publisher> dataModel;

    @PostConstruct
    public void init() {
        dataModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Publisher> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection) {
        if (Objects.isNull(sortField)) sortField = "name";
        String searchText = sprController.getSearchText();
        if (Objects.isNull(searchText) || searchText.isEmpty())
            publisherPage = publisherDao.getAll(pageNumber, count, sortField, sortDirection);
        else
            publisherPage = publisherDao.search(pageNumber, count, sortField, sortDirection, searchText);
        return publisherPage;
    }

    @Override
    public void addAction() {
        selectedPublisher = new Publisher();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        publisherDao.delete(selectedPublisher);
    }

    public List<Publisher> find(String name) {
        return publisherDao.search(name);
    }

    public void save() {
        publisherDao.save(selectedPublisher);
        hideEditDialog();
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').show()");
    }

    private void hideEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').hide()");
    }
}
