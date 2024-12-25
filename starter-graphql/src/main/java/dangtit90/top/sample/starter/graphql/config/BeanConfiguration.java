package dangtit90.top.sample.starter.graphql.config;

import dangtit90.top.sample.starter.graphql.domain.model.Author;
import dangtit90.top.sample.starter.graphql.domain.model.Post;
import dangtit90.top.sample.starter.graphql.repository.AuthorDao;
import dangtit90.top.sample.starter.graphql.repository.PostDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class BeanConfiguration {

    @Bean
    public PostDao postDao() {
        Random random = new Random();
        List<Post> posts = new ArrayList<>();
        for (int postId = 1; postId <= 100; ++postId) {
            int numAuthor = random.nextInt(1, 10);
            for (int authorId = 1; authorId <= numAuthor; ++authorId) {
                Post post = new Post();
                post.setId("Post_" + authorId + "_" + postId);
                post.setTitle("Post title " + authorId + ":" + postId);
                post.setCategory("Post category");
                post.setText("Post " + postId + " by author " + authorId);
                post.setAuthorId("Author_" + authorId);
                posts.add(post);
            }
        }
        return new PostDao(posts);
    }

    @Bean
    public AuthorDao authorDao() {
        List<Author> authors = new ArrayList<>();
        for (int authorId = 1; authorId <= 10; ++authorId) {
            Author author = new Author();
            author.setId("Author_" + authorId);
            author.setName("Author Name " + authorId);
            author.setThumbnail("http://example.com/authors/" + authorId);
            authors.add(author);
        }
        return new AuthorDao(authors);
    }
}