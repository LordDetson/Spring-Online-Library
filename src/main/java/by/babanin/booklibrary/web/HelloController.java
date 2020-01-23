package by.babanin.booklibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloController {
    @GetMapping
    public String hello(HttpServletRequest req, HttpServletResponse resp) {
        return "redirect:" + req.getRequestURL().append("index.xhtml").toString();
    }
}
