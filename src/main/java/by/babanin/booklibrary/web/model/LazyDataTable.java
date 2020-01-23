package by.babanin.booklibrary.web.model;

import by.babanin.booklibrary.web.GeneralController;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter @Setter
public class LazyDataTable<T> extends LazyDataModel<T> {
    private GeneralController<T> controller;

    public LazyDataTable(GeneralController<T> controller) {
        this.controller = controller;
    }

    @Override
    public List<T> load(int first, int count, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        int pageNumber = first / count;
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (Objects.nonNull(sortOrder)) {
            switch (sortOrder) {
                case DESCENDING:
                    sortDirection = Sort.Direction.DESC;
            }
        }
        Page<T> content = controller.getContent(pageNumber, count, sortField, sortDirection);
        this.setRowCount((int) content.getTotalElements());
        return content.getContent();
    }
}
