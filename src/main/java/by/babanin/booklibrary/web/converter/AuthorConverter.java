package by.babanin.booklibrary.web.converter;

import by.babanin.booklibrary.dao.AuthorDao;
import by.babanin.booklibrary.domain.Author;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Author.class)
@Component
public class AuthorConverter implements Converter {
    private final AuthorDao authorDao;

    public AuthorConverter(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return authorDao.getById(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Author) o).getFio();
    }
}
