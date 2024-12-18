package dangtit90.top.sample.product_search.graphql.filter;

import lombok.Data;

@Data
public class ProductFilter {
    private FilterField category;
    private FilterField name;
    private FilterField price;
    private FilterField color;
    private FilterField material;

    private int pageNumber;
    private int pageSize;
    private Boolean isDetail;
    private int sourceFrom;
}
