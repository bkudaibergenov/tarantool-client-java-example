package tarantool.client.example.builder;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import tarantool.client.example.dto.ProductCreateRequestDto;
import tarantool.client.example.dto.ProductDto;
import tarantool.client.example.model.Product;

import java.time.ZonedDateTime;

@UtilityClass
public class ProductBuilder {

    public ProductDto buildProductDto(@NonNull Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .created(product.getCreated())
                .build();
    }

    public Product buildProduct(@NonNull ProductCreateRequestDto productCreateRequestDto) {
        return Product.builder()
                .id(productCreateRequestDto.getId())
                .name(productCreateRequestDto.getName())
                .description(productCreateRequestDto.getDescription())
                .price(productCreateRequestDto.getPrice())
                .quantity(productCreateRequestDto.getQuantity())
                .created(ZonedDateTime.now())
                .build();
    }
}
