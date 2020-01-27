package by.babanin.booklibrary.web;

import by.babanin.booklibrary.web.model.Tab;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ManagedBean
@Component
@RequestScoped
@Getter @Setter
public class SprController {
    private static final String DEFAULT_AUTHOR_LIST_KEY = "tabAuthors";
    private static final String DEFAULT_AUTHOR_LIST_ID = "tabView:authorForm:authorList";

    private final Map<String, Tab> TAB_ID_MAP;

    private String selectedTab = DEFAULT_AUTHOR_LIST_KEY;
    private String listId = DEFAULT_AUTHOR_LIST_ID;
    private String searchText;

    public SprController(AuthorController authorController, GenreController genreController, PublisherController publisherController) {
        TAB_ID_MAP = Collections.unmodifiableMap(new HashMap<String, Tab>() {{
            put(
                    "tabAuthors",
                    Tab.builder()
                            .name("tabAuthors")
                            .listId("tabView:authorForm:authorList")
                            .dialogId("tabView:dialogAuthor")
                            .controller(authorController).build()
            );
            put(
                    "tabGenres",
                    Tab.builder()
                            .name("tabGenres")
                            .listId("tabView:genreForm:genreList")
                            .dialogId("tabView:dialogGenre")
                            .controller(genreController)
                            .build()
            );
            put(
                    "tabPublishers",
                    Tab.builder()
                            .name("tabPublishers")
                            .listId("tabView:publisherForm:publisherList")
                            .dialogId("tabView:dialogPublisher")
                            .controller(publisherController)
                            .build()
            );
        }});
        authorController.setSprController(this);
        genreController.setSprController(this);
        publisherController.setSprController(this);
    }

    public void search() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(listId);
    }

    public void onTabChange(TabChangeEvent event) {
        searchText = null;
        selectedTab = event.getTab().getId();
        listId = TAB_ID_MAP.get(selectedTab).getListId();

        RequestContext.getCurrentInstance().update("searchForm:searchInput");
    }

    public void addAction() {
        TAB_ID_MAP.get(selectedTab).getController().addAction();
        RequestContext.getCurrentInstance().update(TAB_ID_MAP.get(selectedTab).getDialogId());
    }
}
