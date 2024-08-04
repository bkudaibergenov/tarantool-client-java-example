package tarantool.client.example.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tarantool.client.example.model.Product;
import tarantool.client.example.tarantool.exception.TarantoolClientException;
import tarantool.client.example.tarantool.exception.TarantoolRecordNotFoundException;

import java.util.List;

/**
 * Service interface for managing products in the Tarantool database.
 */
public interface ProductService {

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products.
     */
    List<Product> getProducts();

    /**
     * Retrieves a paginated list of products from the database.
     *
     * @param offset the starting point of the records to be fetched.
     * @param limit the maximum number of records to be fetched.
     * @return a list of products based on the specified offset and limit.
     */
    List<Product> fetchProductsByPagination(long offset, int limit);

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return the product with the specified ID.
     * @throws TarantoolRecordNotFoundException if no product with the specified ID is found.
     */
    Product getProductById(@NonNull long id);

    /**
     * Creates or updates a product in the database.
     *
     * @param product the product to be created or updated.
     * @return the created or updated product.
     */
    Product createOrUpdateProduct(@NonNull Product product);

    /**
     * Creates a new product in the database.
     *
     * @param product the product to be created.
     * @return the created product.
     * @throws TarantoolClientException if the product creation fails.
     */
    Product createProduct(@NonNull Product product);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     * @throws TarantoolRecordNotFoundException if no product with the specified ID is found.
     */
    void deleteProduct(@NonNull long id);

    /**
     * Finds products by their name and optionally by description.
     *
     * @param name the name of the product(s) to find.
     * @param description the description of the product(s) to find (optional).
     * @return a list of products matching the specified name and description.
     */
    List<Product> findByNameAndDescription(@NonNull String name, @Nullable String description);

    /**
     * Deletes all products from the database.
     *
     * @throws TarantoolRecordNotFoundException if the deletion fails.
     */
    void deleteAllProducts();
}
