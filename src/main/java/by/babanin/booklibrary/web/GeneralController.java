package by.babanin.booklibrary.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public abstract class GeneralController<T> implements Serializable {
    public abstract Page<T> getContent(int pageNumber, int count, String sortField, Sort.Direction sortDirection);
}
