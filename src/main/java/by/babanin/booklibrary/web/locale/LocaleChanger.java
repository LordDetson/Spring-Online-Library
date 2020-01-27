package by.babanin.booklibrary.web.locale;

import by.babanin.booklibrary.web.util.CookieHelper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable {
    private static final Locale DEFAULT_LOCALE = new Locale("ru");
    private Locale currentLocale = DEFAULT_LOCALE;

    public LocaleChanger() {
        Cookie cookieLang = CookieHelper.getCookie(CookieHelper.COOKIE_LANG);
        if (Objects.isNull(cookieLang)) return;

        currentLocale = new Locale(cookieLang.getValue());
    }

    public void changeLocale(String localCode) {
        currentLocale = new Locale(localCode);
        CookieHelper.setCookie(CookieHelper.COOKIE_LANG, currentLocale.getLanguage(), 3600);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
