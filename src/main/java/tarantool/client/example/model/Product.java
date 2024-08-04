package tarantool.client.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tarantool.client.example.tarantool.annotation.TarantoolSpace;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Represents a product entity stored in the Tarantool database.
 * Annotated with {@link TarantoolSpace} to specify the Tarantool space name.
 * <p>
 * Note: The {@link JsonProperty} annotations are necessary to ensure that
 * the entity correctly maps to the Tarantool space when using Lombok for
 * automatic generation of getters and setters.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TarantoolSpace("Product")
public class Product {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("created")
    private ZonedDateTime created;
}
