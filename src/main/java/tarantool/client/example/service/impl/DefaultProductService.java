package tarantool.client.example.service.impl;

import io.tarantool.client.crud.Condition;
import io.tarantool.client.crud.options.SelectOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tarantool.client.example.exception.TarantoolClientException;
import tarantool.client.example.exception.TarantoolRecordNotFoundException;
import tarantool.client.example.model.Product;
import tarantool.client.example.service.ProductService;
import tarantool.client.example.tarantool.service.TarantoolCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final TarantoolCrudService tarantoolCrudService;


    @Override
    public List<Product> getProducts() {
        return tarantoolCrudService.select(Product.class);
    }

    @Override
    public List<Product> fetchProductsByPagination(long offset, int limit) {
        SelectOptions selectOptions = SelectOptions.builder()
                .withFirst(limit)
                .build();

        List<Product> result = new ArrayList<>();

        while (true) {
            Condition condition = Condition.create(">=", "primary", offset);
            List<Product> selectResult = tarantoolCrudService.select(condition, selectOptions, Product.class);

            result.addAll(selectResult);

            if (selectResult.size() < limit) {
                break;
            }

            offset = selectResult.getLast().getId();

            selectOptions = SelectOptions.builder()
                    .withFirst(limit)
                    .withAfter(selectResult.getLast())
                    .build();
        }

        return result;
    }

    @Override
    public Product getProductById(@NonNull long id) {
        return tarantoolCrudService.selectSingle(Condition.builder()
                                .withFieldIdentifier("primary")
                                .withOperator("=")
                                .withValue(id)
                                .build(),
                        Product.class)
                .orElseThrow(() -> new TarantoolRecordNotFoundException("Product not found"));
    }

    @Override
    public Product createOrUpdateProduct(@NonNull Product product) {
        return tarantoolCrudService.replace(product, Product.class);
    }

    @Override
    public Product createProduct(@NonNull Product product) {
        try {
            return tarantoolCrudService.insert(product, Product.class);
        } catch (Exception e) {
            throw new TarantoolClientException("Failed to create product");
        }
    }

    @Override
    public void deleteProduct(@NonNull long id) {
        tarantoolCrudService.delete(id, Product.class);
    }

    @Override
    public List<Product> findByNameAndDescription(@NonNull String name, @Nullable String description) {
        List<Condition> conditions = Stream.of(
                        Condition.builder()
                                .withFieldIdentifier("name")
                                .withOperator("=")
                                .withValue(name)
                                .build(),
                        description != null ? Condition.builder()
                                .withFieldIdentifier("description")
                                .withOperator("=")
                                .withValue(description)
                                .build() : null
                )
                .filter(Objects::nonNull)
                .toList();

        return tarantoolCrudService.select(conditions, Product.class);
    }

    @Override
    public void deleteAllProducts() {
        if (!tarantoolCrudService.deleteAll(Product.class)) {
            throw new TarantoolRecordNotFoundException("Failed to delete all products");
        }
    }

}
