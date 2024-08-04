package tarantool.client.example.tarantool.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "tarantooldb")
public class TarantoolProperties {

    @NotBlank
    private String user;

    @NotBlank
    private String password;

    @NotNull
    private List<Node> nodes;

    @Getter
    @Setter
    public static class Node {

        private int port;
        private String tag;
    }
}
