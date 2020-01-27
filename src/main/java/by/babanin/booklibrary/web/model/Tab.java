package by.babanin.booklibrary.web.model;

import by.babanin.booklibrary.web.GeneralController;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Tab {
    private String name;
    private String listId;
    private String dialogId;
    private GeneralController<?> controller;
}
