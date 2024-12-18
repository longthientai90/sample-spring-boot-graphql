package dangtit90.top.sample.product_search.resolver;

import dangtit90.top.sample.product_search.domain.model.Product;
import dangtit90.top.sample.product_search.graphql.filter.ProductFilter;
import dangtit90.top.sample.product_search.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    @Autowired
    private ProductSearchService productSearchService;

    @QueryMapping
    public Iterable<Product> productsWithFilter(@Argument ProductFilter filter) {
        return productSearchService.productsWithFilter(filter);
    }
}
