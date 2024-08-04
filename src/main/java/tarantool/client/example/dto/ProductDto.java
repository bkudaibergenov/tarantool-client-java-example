package tarantool.client.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private ZonedDateTime created;
}
