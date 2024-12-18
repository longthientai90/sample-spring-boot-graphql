package dangtit90.top.sample.product_search.domain.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;

    private Category category;
}
