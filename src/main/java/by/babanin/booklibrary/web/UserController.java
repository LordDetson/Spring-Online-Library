package by.babanin.booklibrary.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Objects;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
@Component
@Getter @Setter
public class UserController {

    private String username;
    private String password;

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // есть ли роль у пользователя
    public boolean hasRole(String role) {
        return getCurrentUser().getAuthorities().stream().filter(x -> x.getAuthority().equals("ROLE_" + role)).count() > 0;
    }

    // локализованное сообщение о том, что неправильно ввели логин или пароль
    public String getLoginFailedMessage() {

        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginFailed");

        if (Objects.isNull(obj)) return "";

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

        String message = bundle.getString("login_failed");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginFailed");
        return message;

    }
}
