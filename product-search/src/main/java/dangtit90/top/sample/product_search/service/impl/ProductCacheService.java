package dangtit90.top.sample.product_search.service.impl;

import dangtit90.top.sample.product_search.domain.constant.CacheConstant;
import dangtit90.top.sample.product_search.domain.model.Product;
import dangtit90.top.sample.product_search.graphql.filter.FilterField;
import dangtit90.top.sample.product_search.graphql.filter.ProductFilter;
import dangtit90.top.sample.product_search.service.ProductSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductCacheService implements ProductSearchService {

    @Autowired
    CacheManager cacheManager;

    @Override
    public List<Product> productsWithFilter(ProductFilter filter) {
        List<Product> response = new ArrayList<>();
        Cache cache = cacheManager.getCache(CacheConstant.CACHE_ALL_PRODUCTS);
        com.github.benmanes.caffeine.cache.Cache<Long, Product> nativeCache = (com.github.benmanes.caffeine.cache.Cache<Long, Product>)cache.getNativeCache();
        Collection<Product> allProducts = nativeCache.asMap().values();

        // paging
        int limit = filter.getPageSize();
        int offset = filter.getPageNumber() * limit;

        // filter
        response = allProducts.parallelStream().filter(buildFilter(filter))
                .sorted(Comparator.comparing(o -> o.getPrice()))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        return response;
    }

    private Predicate<Product> buildFilter(ProductFilter filter) {
        Predicate<Product> spec = null;
        if (Objects.nonNull(filter.getCategory())) {
            spec = (spec == null ? byCategory(filter.getCategory()) : spec.and(byCategory(filter.getCategory())));
        }
        if (Objects.nonNull(filter.getPrice())) {
            spec = (spec == null ? byPrice(filter.getPrice()) : spec.and(byPrice(filter.getPrice())));
        }
        if (Objects.nonNull(filter.getName())) {
            spec = (spec == null ? byName(filter.getName()) : spec.and(byName(filter.getName())));
        }
        if (Objects.nonNull(filter.getColor())) {
            spec = (spec == null ? byColor(filter.getColor()) : spec.and(byColor(filter.getColor())));
        }
        return spec;
    }


    private Predicate<Product> byCategory(FilterField filter) {
        int value = Integer.parseInt(filter.getValue());
        Predicate<Product> byCategory = null;

        // build filter
        if (filter.getOperator().equals("eq")) {
            byCategory = p -> p.getCategory().getId() == value;
        }

        return byCategory;
    }

    private Predicate<Product> byPrice(FilterField filter) {
        int value = Integer.parseInt(filter.getValue());
        Predicate<Product> byPrice = null;

        // build filter
        if (filter.getOperator().equals("eq")) {
            byPrice = p -> p.getPrice() == value;
        } else if (filter.getOperator().equals("lt")) {
            byPrice = p -> p.getPrice() < value;
        } else if (filter.getOperator().equals("le")) {
            byPrice = p -> p.getPrice() <= value;
        } else if (filter.getOperator().equals("gt")) {
            byPrice = p -> p.getPrice() > value;
        } else if (filter.getOperator().equals("ge")) {
            byPrice = p -> p.getPrice() >= value;
        }
        return byPrice;
    }

    private Predicate<Product> byName(FilterField filter) {
        String value = filter.getValue();
        Predicate<Product> byName = null;
        if(StringUtils.isEmpty(value)) return byName;

        // build filter
        if (filter.getOperator().equals("eq")) {
            byName = p -> value.equals(p.getName());
        } else if (filter.getOperator().equals("endsWith")) {
            byName = p -> p.getName().endsWith(value);
        } else if (filter.getOperator().equals("startsWith")) {
            byName = p -> p.getName().startsWith(value);
        } else if (filter.getOperator().equals("contains")) {
            byName = p -> p.getName().contains(value);
        }
        return byName;
    }

    private Predicate<Product> byColor(FilterField filter) {
        String value = filter.getValue();
        Predicate<Product> byColor = null;
        if(StringUtils.isEmpty(value)) return byColor;

        // build filter
        if (filter.getOperator().equals("eq")) {
            byColor = p -> value.equals(p.getColor());
        } else if (filter.getOperator().equals("endsWith")) {
            byColor = p -> p.getColor().endsWith(value);
        } else if (filter.getOperator().equals("startsWith")) {
            byColor = p -> p.getColor().startsWith(value);
        } else if (filter.getOperator().equals("contains")) {
            byColor = p -> p.getColor().contains(value);
        }
        return byColor;
    }
}
