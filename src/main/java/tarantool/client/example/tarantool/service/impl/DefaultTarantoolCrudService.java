package tarantool.client.example.tarantool.service.impl;

import io.tarantool.client.crud.Condition;
import io.tarantool.client.crud.TarantoolCrudClient;
import io.tarantool.client.crud.options.SelectOptions;
import io.tarantool.mapping.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tarantool.client.example.tarantool.annotation.TarantoolSpace;
import tarantool.client.example.tarantool.service.TarantoolCrudService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultTarantoolCrudService implements TarantoolCrudService {

    private final TarantoolCrudClient tarantoolCrudClient;


    @Override
    public <T> List<T> select(@NonNull Class<T> clazz) {
        return executeSelect(Collections.emptyList(), null, clazz);
    }

    @Override
    public <T> List<T> select(@NonNull Condition condition, @NonNull Class<T> clazz) {
        return executeSelect(Collections.singletonList(condition), null, clazz);
    }

    @Override
    public <T> List<T> select(@NonNull Condition condition, @NonNull SelectOptions selectOptions, @NonNull Class<T> clazz) {
        return executeSelect(Collections.singletonList(condition), selectOptions, clazz);
    }

    @Override
    public <T> List<T> select(@NonNull List<Condition> conditions, @NonNull Class<T> clazz) {
        return executeSelect(conditions, null, clazz);
    }

    @Override
    public <T> Optional<T> selectSingle(@NonNull Condition condition, @NonNull Class<T> clazz) {
        return selectSingle(Collections.singletonList(condition), clazz);
    }

    @Override
    public <T> Optional<T> selectSingle(@NonNull List<Condition> conditions, @NonNull Class<T> clazz) {
        List<T> result = executeSelect(conditions, null, clazz);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public <T> T replace(@NonNull T entity, @NonNull Class<T> clazz) {
        return executeInsertOrUpdate(entity, clazz);
    }

    @Override
    public <T> T insert(@NonNull T entity, @NonNull Class<T> clazz) {
        return executeInsertOrUpdate(entity, clazz);
    }

    @Override
    public <T> void delete(@NonNull Object key, @NonNull Class<T> clazz) {
        String spaceName = getTarantoolSpaceName(clazz);
        tarantoolCrudClient.space(spaceName).delete(key);
    }

    @Override
    public <T> boolean deleteAll(@NonNull Class<T> clazz) {
        String spaceName = getTarantoolSpaceName(clazz);
        Boolean result = tarantoolCrudClient.space(spaceName).truncate().join();
        return Boolean.TRUE.equals(result);
    }

    private <T> List<T> executeSelect(@NonNull List<Condition> conditions, SelectOptions options, @NonNull Class<T> clazz) {
        String spaceName = getTarantoolSpaceName(clazz);
        if (options != null) {
            return tarantoolCrudClient.space(spaceName)
                    .select(conditions, options, clazz).join()
                    .stream().map(Tuple::get)
                    .toList();
        } else {
            return tarantoolCrudClient.space(spaceName)
                    .select(conditions, clazz).join()
                    .stream().map(Tuple::get)
                    .toList();
        }
    }

    private <T> T executeInsertOrUpdate(@NonNull T entity, @NonNull Class<T> clazz) {
        String spaceName = getTarantoolSpaceName(clazz);
        return tarantoolCrudClient.space(spaceName)
                .replace(entity, clazz).join().get();
    }

    private <T> String getTarantoolSpaceName(@NonNull Class<T> clazz) {
        TarantoolSpace spaceAnnotation = clazz.getAnnotation(TarantoolSpace.class);
        if (spaceAnnotation != null) {
            return spaceAnnotation.value();
        } else {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with @TarantoolSpace");
        }
    }
}
