package by.babanin.booklibrary.web.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class CookieHelper {
    public static final String COOKIE_LANG = "book_library_lang";

    public static void setCookie(String name, String value, int expiry) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        Cookie cookie = null;

        Cookie[] userCookies = req.getCookies();
        if (Objects.nonNull(userCookies) && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (Objects.isNull(cookie)) {
            cookie = new Cookie(name, value);
        } else {
            cookie.setValue(value);
        }

        cookie.setPath(req.getContextPath());
        cookie.setMaxAge(expiry);
        resp.addCookie(cookie);
    }

    public static Cookie getCookie(String name) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Cookie[] userCookies = req.getCookies();
        if (Objects.nonNull(userCookies) && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    return userCookies[i];
                }
            }
        }
        return null;
    }
}
