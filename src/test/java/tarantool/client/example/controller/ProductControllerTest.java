package tarantool.client.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tarantool.client.example.builder.ProductBuilder;
import tarantool.client.example.dto.ProductCreateRequestDto;
import tarantool.client.example.dto.ProductDto;
import tarantool.client.example.model.Product;
import tarantool.client.example.service.ProductService;
import tarantool.client.example.tarantool.exception.TarantoolClientException;
import tarantool.client.example.tarantool.exception.TarantoolRecordNotFoundException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void shouldReturnListOfProducts() throws Exception {
        // Given
        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(10.00))
                        .quantity(100)
                        .created(ZonedDateTime.now())
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Product 2")
                        .description("Description 2")
                        .price(BigDecimal.valueOf(20.00))
                        .quantity(200)
                        .created(ZonedDateTime.now())
                        .build()
        );

        List<ProductDto> productDtoList = products.stream()
                .map(ProductBuilder::buildProductDto)
                .toList();

        // When
        when(productService.getProducts()).thenReturn(products);

        // Then
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDtoList)));
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() throws Exception {
        // When
        when(productService.getProducts()).thenReturn(List.of());

        // Then
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    void shouldReturnPaginatedListOfProducts() throws Exception {
        // Given
        long offset = 0;
        int limit = 2;

        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(10.00))
                        .quantity(100)
                        .created(ZonedDateTime.now())
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Product 2")
                        .description("Description 2")
                        .price(BigDecimal.valueOf(20.00))
                        .quantity(200)
                        .created(ZonedDateTime.now())
                        .build()
        );

        List<ProductDto> productDtoList = products.stream()
                .map(ProductBuilder::buildProductDto)
                .toList();

        // When
        when(productService.fetchProductsByPagination(offset, limit)).thenReturn(products);

        // Then
        mockMvc.perform(get("/api/v1/products/pagination")
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDtoList)));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        // Given
        long productId = 1L;

        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.00))
                .quantity(100)
                .created(ZonedDateTime.now())
                .build();

        ProductDto productDto = ProductBuilder.buildProductDto(product);

        // When
        when(productService.getProductById(productId)).thenReturn(product);

        // Then
        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() throws Exception {
        // Given
        long productId = 1L;

        // When
        when(productService.getProductById(productId))
                .thenThrow(new TarantoolRecordNotFoundException("Product not found"));

        // Then
        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateOrUpdateProduct() throws Exception {
        // Given
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.00))
                .quantity(100)
                .build();

        Product product = ProductBuilder.buildProduct(productCreateRequestDto);
        ProductDto productDto = ProductBuilder.buildProductDto(product);

        // When
        when(productService.createOrUpdateProduct(any()))
                .thenReturn(product);

        // Then
        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        // Given
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.00))
                .quantity(100)
                .build();

        Product product = ProductBuilder.buildProduct(productCreateRequestDto);
        ProductDto productDto = ProductBuilder.buildProductDto(product);

        // When
        when(productService.createProduct(any()))
                .thenReturn(product);

        // Then
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    void shouldReturnInternalServerErrorWhenProductAlreadyExists() throws Exception {
        // Given
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.00))
                .quantity(100)
                .build();

        // When
        when(productService.createProduct(any()))
                .thenThrow(new TarantoolClientException("Product already exists"));

        // Then
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequestDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // Given
        long productId = 1L;

        // When
        doNothing().when(productService).deleteProduct(productId);

        // Then
        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindByNameAndDescription() throws Exception {
        // Given
        String name = "Product 1";
        String description = "Description 1";

        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(10.00))
                        .quantity(100)
                        .created(ZonedDateTime.now())
                        .build()
        );

        List<ProductDto> productDtoList = products.stream()
                .map(ProductBuilder::buildProductDto)
                .toList();

        // When
        when(productService.findByNameAndDescription(name, description))
                .thenReturn(products);

        // Then
        mockMvc.perform(get("/api/v1/products/search")
                        .param("name", name)
                        .param("description", description)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDtoList)));
    }

    @Test
    void shouldFindByName() throws Exception {
        // Given
        String name = "Product 1";

        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(10.00))
                        .quantity(100)
                        .created(ZonedDateTime.now())
                        .build()
        );

        List<ProductDto> productDtoList = products.stream()
                .map(ProductBuilder::buildProductDto)
                .toList();

        // When
        when(productService.findByNameAndDescription(name, null))
                .thenReturn(products);

        // Then
        mockMvc.perform(get("/api/v1/products/search")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDtoList)));
    }

    @Test
    void shouldDeleteAllProducts() throws Exception {
        // When
        doNothing().when(productService).deleteAllProducts();

        // Then
        mockMvc.perform(delete("/api/v1/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
