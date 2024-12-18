package dangtit90.top.sample.product_search;

import dangtit90.top.sample.product_search.service.FakerProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ProductSearchGraphQL implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductSearchGraphQL.class, args);
    }

    @Autowired
    FakerProductService fakerProductService;

    @Override
    public void run(String... args) throws Exception {
        fakerProductService.fakeCategories();
        log.info("DONE ALL fakeCategories");
        fakerProductService.fakeProducts();
        log.info("DONE ALL fakeProducts");
    }
}
