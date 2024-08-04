package tarantool.client.example.tarantool.config;

import io.tarantool.client.crud.TarantoolCrudClient;
import io.tarantool.client.factory.TarantoolFactory;
import io.tarantool.pool.InstanceConnectionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tarantool.client.example.tarantool.properties.TarantoolProperties;

@Configuration
@RequiredArgsConstructor
public class TarantoolClientConfig {

    private final TarantoolProperties tarantoolProperties;

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
