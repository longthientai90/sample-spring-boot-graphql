package dangtit90.top.sample.starter.graphql.repository;

import dangtit90.top.sample.starter.graphql.domain.model.Author;
import dangtit90.top.sample.starter.graphql.domain.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDao {

    private final List<Post> posts;

    public PostDao(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return posts.stream()
                .skip(offset)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Post> getAuthorPosts(String author) {
        return posts.stream()
                .filter(post -> author.equals(post.getAuthorId()))
                .collect(Collectors.toList());
    }

    public void savePost(Post post) {
        posts.add(post);
    }

    public Post getPost(String id) {
        return posts.stream()
                .filter(post -> id.equals(post.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}