package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/posts", "/posts/*"})
public class MainServlet extends HttpServlet {

    private PostController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        controller = new PostController(service);
        log("Method init :)");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        controller.save(request.getReader(), response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/api/posts") || path.equals("/api/posts/")) {
            controller.all(response);
        }
        else {
            if (path.matches("/api/posts/\\d+")) {
                String idString = path.substring(11);
                long id = Long.parseLong(idString);
                try {
                    controller.getById(id, response);
                } catch (Exception exception) {
                    response.getWriter().write("Out of post in memory with id " + id + "\n");
                }
            } else {
                response.getWriter().write("Wrong id" + "\n");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.matches("/api/posts/\\d+")) {
            String idString = path.substring(11);
            long id = Long.parseLong(idString);
            try {
                controller.removeById(id, response);
            } catch (Exception exception) {
                response.getWriter().write("Out of post in memory with id " + id + "\n");
            }
        } else {
            response.getWriter().write("Wrong id" + "\n");
        }
    }

    @Override
    public void destroy() {
    }
}
