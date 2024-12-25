package dangtit90.top.sample.starter.graphql.resolver;

import dangtit90.top.sample.starter.graphql.domain.model.Author;
import dangtit90.top.sample.starter.graphql.domain.model.Post;
import dangtit90.top.sample.starter.graphql.repository.AuthorDao;
import dangtit90.top.sample.starter.graphql.repository.PostDao;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final PostDao postDao;
    private final AuthorDao authorDao;

    public AuthorController(PostDao postDao, AuthorDao authorDao) {
        this.postDao = postDao;
        this.authorDao = authorDao;
    }

    @SchemaMapping
    public List<Post> posts(Author author) {
        return postDao.getAuthorPosts(author.getId());
    }

    @QueryMapping
    public Author getAuthor(@Argument String id) {
        return authorDao.getAuthor(id);
    }
}