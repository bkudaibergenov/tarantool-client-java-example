package tarantool.client.example.tarantool.bootstrap;

import io.tarantool.client.crud.TarantoolCrudClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TarantoolSpaceScanner implements CommandLineRunner {

    private final TarantoolCrudClient tarantoolCrudClient;

    @Override
    public void run(String... args) {
        tarantool.client.example.tarantool.util.TarantoolSpaceScanner.getTarantoolSpaces()
                .forEach(space -> tarantoolCrudClient.space(space).count().join());
    }
}
