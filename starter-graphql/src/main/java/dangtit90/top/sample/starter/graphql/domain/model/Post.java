package dangtit90.top.sample.starter.graphql.domain.model;

import lombok.Data;

@Data
public class Post {

    private String id;
    private String title;
    private String text;
    private String category;
    private String authorId;

}