package by.babanin.booklibrary.servlet;

import by.babanin.booklibrary.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class BookContentProvider extends HttpServlet {
    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServletOutputStream out = resp.getOutputStream()) {
            Long id = Long.valueOf(req.getParameter("id"));
            long viewCount = Long.parseLong(req.getParameter("viewCount"));
            BookDao bookDao = context.getBean(BookDao.class);
            byte[] content = bookDao.getContent(id);
            if (Objects.nonNull(content)) {
                bookDao.updateViewCount(viewCount + 1, id);
                resp.setContentType("application/pdf");
                resp.setContentLength(content.length);
                out.write(content);
            } else {
                resp.sendRedirect(req.getContextPath()+"/pages/error/error-pdf.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
