package dangtit90.top.sample.starter.graphql.resolver;

import dangtit90.top.sample.starter.graphql.domain.model.Author;
import dangtit90.top.sample.starter.graphql.domain.model.Post;
import dangtit90.top.sample.starter.graphql.repository.PostDao;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final PostDao postDao;

    public AuthorController(PostDao postDao) {
        this.postDao = postDao;
    }

    @SchemaMapping
    public List<Post> posts(Author author) {
        return postDao.getAuthorPosts(author.getId());
    }
}