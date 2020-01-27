package by.babanin.booklibrary.web.util;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

@Log
@Aspect
@Component
public class AspectMessageHandler {

    @Around("execution(* by.babanin.booklibrary.dao.*.delete(..))")
    public void deleteConstrain(ProceedingJoinPoint joinPoint) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle msg = context.getApplication().getResourceBundle(context, "msg");
        try {
            joinPoint.proceed();
            context.addMessage(null, new FacesMessage(null, msg.getString("deleted")));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        RequestContext.getCurrentInstance().update("info");
    }

    @Around("execution(* by.babanin.booklibrary.dao.*.save(..))")
    public void addNewSprValue(ProceedingJoinPoint joinPoint) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle msg = context.getApplication().getResourceBundle(context, "msg");
        try {
            Object o = joinPoint.proceed();
            context.addMessage(null, new FacesMessage(null, msg.getString("added") + ": \"" + o.toString() + "\""));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        RequestContext.getCurrentInstance().update("info");
    }
}
