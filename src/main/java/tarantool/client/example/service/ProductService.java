package tarantool.client.example.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tarantool.client.example.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    List<Product> fetchProductsByPagination(long offset, int limit);

    Product getProductById(@NonNull long id);

    Product createOrUpdateProduct(@NonNull Product product);

    Product createProduct(@NonNull Product product);

    void deleteProduct(@NonNull long id);

    List<Product> findByNameAndDescription(@NonNull String name, @Nullable String description);

    void deleteAllProducts();
}
