package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.getWriter().write("Method GET -> get all posts\n");
        response.setContentType(APPLICATION_JSON);

        //List<Post> posts = service.all();
        //Gson gson = new Gson();
        //response.getWriter().print(gson.toJson(posts));

        List<Post> data = service.all();
        response.getWriter().print("Messages (" + data.size() + ")\n");
        for (Post post : data) {
            printMessage(response, post);
        }
    }

    public void getById(long id, HttpServletResponse response) throws Exception {
        response.getWriter().write("Method GET -> get by id" + "\n");
        response.setContentType(APPLICATION_JSON);
        Post data = service.getById(id);
        printMessage(response, data);

//        Gson gson = new Gson();
//        response.getWriter().write(gson.toJson(data));
//        response.getWriter().write("\n");
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.getWriter().write("Method POST -> save\n");
        response.setContentType(APPLICATION_JSON);
        Gson gson = new Gson();
        Post post = gson.fromJson(body, Post.class);
        Post data = service.save(post);
        printMessage(response, data);
//        response.getWriter().write(gson.toJson(data));
//        response.getWriter().write("\n");

    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.getWriter().write("Method DELETE -> delete by id" + "\n");
        service.removeById(id);
    }

    private void printMessage(HttpServletResponse response, Post post) throws IOException {
        response.getWriter().print("Message id: " + post.getId() + "\n");
        response.getWriter().print("Message content: " + post.getContent() + "\n");
    }
}
