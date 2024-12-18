package dangtit90.top.sample.product_search.service;

import com.github.javafaker.Faker;
import dangtit90.top.sample.product_search.domain.constant.CacheConstant;
import dangtit90.top.sample.product_search.domain.model.Category;
import dangtit90.top.sample.product_search.domain.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class FakerProductService {
    @Autowired
    CacheManager cacheManager;

    public void fakeCategories() {
        Cache cache = cacheManager.getCache(CacheConstant.CACHE_ALL_CATEGORIES);

        Faker faker = new Faker();
        List<String> readyNames = new ArrayList<>();

        int idIdx = 0;
        // 10 x 5.000 = 50.000
        for (int i = 0; i < 10; i++) {
            List<Category> categories = new ArrayList<>();
            for (int j = 0; j < 5000; j++) {
                Category category = new Category();
                String cateName = StringUtils.EMPTY;
                int counter = 0;
                // try 10
                while (true) {
                    cateName = faker.company().industry();
                    if (!readyNames.contains(cateName)) {
                        break;
                    } else {
                        counter++;
                    }
                    if(counter == 3) {
                        break;
                    }
                }
                category.setId(idIdx);
                category.setName(cateName);
                category.setDescription(faker.company().name());
                categories.add(category);
                readyNames.add(cateName);
                cache.put(category.getId(), category);
                idIdx++;
            }
            log.info("fakeCategories DONE LOOP {}", i);
        }
    }

    public void fakeProducts() {
        Cache cache = cacheManager.getCache(CacheConstant.CACHE_ALL_PRODUCTS);
        Faker faker = new Faker();
        List<Category> categories = getAllCategoriesFromCache();
        int totalCate = categories.size();

        Random random = new Random();
        List<String> readyNames = new ArrayList<>();

        int idIdx = 0;
        // 400 x 5.000 = 2.000.000
        for (int i = 0; i < 400; i++) {
            List<Product> products = new ArrayList<>();
            for (int j = 0; j < 5000; j++) {
                Product product = new Product();
                Category category = categories.get(random.nextInt(totalCate - 1));
                String productName = StringUtils.EMPTY;
                int counter = 0;
                // try 5
                while (true) {
                    productName = faker.commerce().productName();
                    if (!readyNames.contains(productName)) {
                        break;
                    }
                    counter++;
                    if(counter == 3) {
                        productName = faker.book().title();
                        break;
                    }
                }
                product.setId(idIdx);
                product.setCategory(category);
                product.setName(faker.commerce().productName());
                product.setColor(faker.commerce().color());
                product.setPrice(random.nextInt(1, 4000));
                product.setMaterial(faker.commerce().material());
                product.setDescription(faker.book().title());
                products.add(product);
                cache.put(product.getId(), product);
                idIdx++;
                readyNames.add(productName);
            }
            log.info("fakeProducts DONE LOOP {}", i);
        }
    }

    private List<Category> getAllCategoriesFromCache() {
        Cache cache = cacheManager.getCache(CacheConstant.CACHE_ALL_CATEGORIES);
        com.github.benmanes.caffeine.cache.Cache<Integer, Category> nativeCache = (com.github.benmanes.caffeine.cache.Cache<Integer, Category>)cache.getNativeCache();
        return new ArrayList(nativeCache.asMap().values());
    }

    private Date getRandomDate(int yearFrom, int yearTo) {
        Random random = new Random();
        int randomYear = random.nextInt(yearFrom, yearTo);
        int dayOfYear = random.nextInt(1, 365);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, randomYear);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
