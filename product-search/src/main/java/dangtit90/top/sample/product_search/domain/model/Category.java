package dangtit90.top.sample.product_search.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private int id;
    private int parentId;
    private String name;
    private String description;
    List<Product> products;
}
