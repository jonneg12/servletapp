package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PostRepository {

    List<Post> posts;

    public PostRepository() {
        posts = new CopyOnWriteArrayList<>();
        posts.add(new Post("test message"));
    }

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        for (Post post : posts) {
            if(post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        long id = post.getId();
        String content = post.getContent();
        if(id == 0) {
        posts.add(new Post(post.getContent()));
        } else {
            for (Post postInList : posts) {
                if(postInList.getId() == id){
                    postInList.setContent(content);
                    return post;
                }
            }

        }
        return post;
    }

    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}
