package tarantool.client.example.tarantool.config;

import io.tarantool.client.crud.TarantoolCrudClient;
import io.tarantool.client.factory.TarantoolFactory;
import io.tarantool.pool.InstanceConnectionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tarantool.client.example.tarantool.properties.TarantoolProperties;

/**
 * Configuration class for setting up the Tarantool CRUD client.
 * <p>
 * @author Bekzhan Kudaibergenov
 */
@Configuration
@RequiredArgsConstructor
public class TarantoolClientConfig {

    private final TarantoolProperties tarantoolProperties;

    /**
     * Creates and configures a {@link TarantoolCrudClient} bean using the properties defined in
     * {@link TarantoolProperties}.
     *
     * @return a configured instance of {@link TarantoolCrudClient}.
     * @throws Exception if there is an error during the client creation.
     *
     * @author Bekzhan Kudaibergenov
     */
    @Bean
    public TarantoolCrudClient tarantoolCrudClient() throws Exception {
        return TarantoolFactory.crud()
                .withGroups(
                        tarantoolProperties.getNodes().stream()
                                .map(node -> InstanceConnectionGroup.builder()
                                        .withPort(node.getPort())
                                        .withUser(tarantoolProperties.getUser())
                                        .withPassword(tarantoolProperties.getPassword())
                                        .withTag(node.getTag())
                                        .build()
                                ).toList()
                ).build();
    }

}
