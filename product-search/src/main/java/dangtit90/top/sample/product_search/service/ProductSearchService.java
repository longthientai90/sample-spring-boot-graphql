package dangtit90.top.sample.product_search.service;

import dangtit90.top.sample.product_search.domain.model.Product;
import dangtit90.top.sample.product_search.graphql.filter.ProductFilter;

import java.util.List;

public interface ProductSearchService {
    public List<Product> productsWithFilter(ProductFilter filter);
}
