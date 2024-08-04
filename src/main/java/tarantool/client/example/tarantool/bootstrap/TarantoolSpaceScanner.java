package tarantool.client.example.tarantool.bootstrap;

import io.tarantool.client.crud.TarantoolCrudClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Scans all classes annotated with {@link tarantool.client.example.tarantool.annotation.TarantoolSpace}
 * and performs a test count query to ensure the space is created in the Tarantool database.
 */
@Component
@RequiredArgsConstructor
public class TarantoolSpaceScanner implements CommandLineRunner {

    private final TarantoolCrudClient tarantoolCrudClient;

    /**
     * Executes on application startup to scan for Tarantool spaces and verify their existence in the database.
     *
     * @param args command line arguments (not used).
     */
    @Override
    public void run(String... args) {
        tarantool.client.example.tarantool.util.TarantoolSpaceScanner.getTarantoolSpaces()
                .forEach(space -> tarantoolCrudClient.space(space).count().join());
    }
}
