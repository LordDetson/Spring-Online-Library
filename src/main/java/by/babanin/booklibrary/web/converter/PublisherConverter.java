package by.babanin.booklibrary.web.converter;

import by.babanin.booklibrary.dao.PublisherDao;
import by.babanin.booklibrary.domain.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Publisher.class)
@Component
public class PublisherConverter implements Converter {
    private final PublisherDao publisherDao;

    public PublisherConverter(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return publisherDao.search(0, 1, "name", Sort.Direction.ASC, s).getContent().get(0);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Publisher) o).getName();
    }
}
