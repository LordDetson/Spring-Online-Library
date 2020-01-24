package by.babanin.booklibrary.web.converter;

import by.babanin.booklibrary.dao.PublisherDao;
import by.babanin.booklibrary.domain.Publisher;
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
        return publisherDao.getById(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Publisher) o).getName();
    }
}
