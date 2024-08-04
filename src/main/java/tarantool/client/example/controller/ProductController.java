package tarantool.client.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tarantool.client.example.builder.ProductBuilder;
import tarantool.client.example.dto.ProductCreateRequestDto;
import tarantool.client.example.dto.ProductDto;
import tarantool.client.example.model.Product;
import tarantool.client.example.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts().stream()
                .map(ProductBuilder::buildProductDto)
                .toList();
    }

    @GetMapping("/pagination")
    public List<ProductDto> fetchProductsByPagination(@RequestParam("offset") long offset,
                                                      @RequestParam("limit") int limit) {

        return productService.fetchProductsByPagination(offset, limit).stream()
                .map(ProductBuilder::buildProductDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        Product product = productService.getProductById(id);
        return ProductBuilder.buildProductDto(product);
    }

    @PutMapping
    public ProductDto createOrUpdateProduct(@RequestBody @Valid ProductCreateRequestDto productCreateRequestDto) {
        Product product = ProductBuilder.buildProduct(productCreateRequestDto);
        return ProductBuilder.buildProductDto(productService.createOrUpdateProduct(product));
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid ProductCreateRequestDto productCreateRequestDto) {
        Product product = ProductBuilder.buildProduct(productCreateRequestDto);
        return ProductBuilder.buildProductDto(productService.createProduct(product));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<ProductDto> findByNameAndDescription(@RequestParam("name") String name,
                                                     @RequestParam(value = "description", required = false) String description) {

        return productService.findByNameAndDescription(name, description).stream()
                .map(ProductBuilder::buildProductDto)
                .toList();
    }

    @DeleteMapping("/all")
    public void deleteAllProducts() {
        productService.deleteAllProducts();
    }

}
