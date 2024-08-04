package tarantool.client.example.tarantool.service;

import io.tarantool.client.crud.Condition;
import io.tarantool.client.crud.options.SelectOptions;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for CRUD operations on Tarantool spaces.
 * <p>
 * @author Bekzhan Kudaibergenov
 */
public interface TarantoolCrudService {

    /**
     * Selects all records from a Tarantool space represented by the specified class.
     *
     * @param clazz the class representing the Tarantool space.
     * @param <T>   the type of the records.
     * @return a list of all records from the specified space.
     */
    <T> List<T> select(@NonNull Class<T> clazz);

    /**
     * Selects records from a Tarantool space that match the specified condition.
     *
     * @param condition the condition to match records.
     * @param clazz     the class representing the Tarantool space.
     * @param <T>       the type of the records.
     * @return a list of records that match the specified condition.
     */
    <T> List<T> select(@NonNull Condition condition,
                       @NonNull Class<T> clazz);

    /**
     * Selects records from a Tarantool space that match the specified condition and options.
     *
     * @param condition     the condition to match records.
     * @param selectOptions the options for selecting records.
     * @param clazz         the class representing the Tarantool space.
     * @param <T>           the type of the records.
     * @return a list of records that match the specified condition and options.
     */
    <T> List<T> select(@NonNull Condition condition,
                       @NonNull SelectOptions selectOptions,
                       @NonNull Class<T> clazz);

    /**
     * Selects records from a Tarantool space that match the specified conditions.
     *
     * @param conditions the conditions to match records.
     * @param clazz      the class representing the Tarantool space.
     * @param <T>        the type of the records.
     * @return a list of records that match the specified conditions.
     */
    <T> List<T> select(@NonNull List<Condition> conditions,
                       @NonNull Class<T> clazz);

    /**
     * Selects a single record from a Tarantool space that matches the specified condition.
     *
     * @param condition the condition to match the record.
     * @param clazz     the class representing the Tarantool space.
     * @param <T>       the type of the record.
     * @return an optional containing the matching record, or empty if no record matches.
     */
    <T> Optional<T> selectSingle(@NonNull Condition condition,
                                 @NonNull Class<T> clazz);

    /**
     * Selects a single record from a Tarantool space that matches the specified conditions.
     *
     * @param conditions the conditions to match the record.
     * @param clazz      the class representing the Tarantool space.
     * @param <T>        the type of the record.
     * @return an optional containing the matching record, or empty if no record matches.
     */
    <T> Optional<T> selectSingle(@NonNull List<Condition> conditions,
                                 @NonNull Class<T> clazz);

    /**
     * Replaces an existing record or inserts a new record in a Tarantool space.
     *
     * @param entity the record to replace or insert.
     * @param clazz  the class representing the Tarantool space.
     * @param <T>    the type of the record.
     * @return the replaced or inserted record.
     */
    <T> T replace(@NonNull T entity,
                  @NonNull Class<T> clazz);

    /**
     * Inserts a new record in a Tarantool space.
     *
     * @param entity the record to insert.
     * @param clazz  the class representing the Tarantool space.
     * @param <T>    the type of the record.
     * @return the inserted record.
     */
    <T> T insert(@NonNull T entity,
                 @NonNull Class<T> clazz);

    /**
     * Deletes a record from a Tarantool space by its key.
     *
     * @param key   the key of the record to delete.
     * @param clazz the class representing the Tarantool space.
     * @param <T>   the type of the record.
     */
    <T> void delete(@NonNull Object key,
                    @NonNull Class<T> clazz);

    /**
     * Deletes all records from a Tarantool space.
     *
     * @param clazz the class representing the Tarantool space.
     * @param <T>   the type of the records.
     * @return true if the operation was successful, false otherwise.
     */
    <T> boolean deleteAll(@NonNull Class<T> clazz);
}
