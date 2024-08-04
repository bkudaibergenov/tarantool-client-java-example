package tarantool.client.example.tarantool.service;

import io.tarantool.client.crud.Condition;
import io.tarantool.client.crud.options.SelectOptions;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TarantoolCrudService {

    <T> List<T> select(@NonNull Class<T> clazz);

    <T> List<T> select(@NonNull Condition condition,
                       @NonNull Class<T> clazz);

    <T> List<T> select(@NonNull Condition condition,
                       @NonNull SelectOptions selectOptions,
                       @NonNull Class<T> clazz);

    <T> List<T> select(@NonNull List<Condition> conditions,
                       @NonNull Class<T> clazz);

    <T> Optional<T> selectSingle(@NonNull Condition condition,
                                 @NonNull Class<T> clazz);

    <T> Optional<T> selectSingle(@NonNull List<Condition> conditions,
                                 @NonNull Class<T> clazz);

    <T> T replace(@NonNull T entity,
                  @NonNull Class<T> clazz);

    <T> T insert(@NonNull T entity,
                 @NonNull Class<T> clazz);

    <T> void delete(@NonNull Object key,
                    @NonNull Class<T> clazz);

    <T> boolean deleteAll(@NonNull Class<T> clazz);
}
