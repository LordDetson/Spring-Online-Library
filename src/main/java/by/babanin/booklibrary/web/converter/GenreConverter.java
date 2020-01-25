package by.babanin.booklibrary.web.converter;

import by.babanin.booklibrary.dao.GenreDao;
import by.babanin.booklibrary.domain.Genre;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Genre.class)
@Component
public class GenreConverter implements Converter {
    private final GenreDao genreDao;

    public GenreConverter(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return genreDao.search(0, 1, "name", Sort.Direction.ASC, s).getContent().get(0);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Genre) o).getName();
    }
}
