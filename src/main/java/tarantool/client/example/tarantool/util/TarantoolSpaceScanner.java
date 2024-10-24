package tarantool.client.example.tarantool.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import tarantool.client.example.tarantool.annotation.TarantoolSpace;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class for scanning and retrieving the names of all Tarantool spaces annotated with {@link TarantoolSpace}.
 * <p>
 * @author Bekzhan Kudaibergenov
 */
@Slf4j
@UtilityClass
public class TarantoolSpaceScanner {

    /**
     * Scans the classpath for classes annotated with {@link TarantoolSpace} and retrieves their space names.
     *
     * @return a set of names of Tarantool spaces.
     */
    public Set<String> getTarantoolSpaces() {
        Set<String> tarantoolSpaceNames = new HashSet<>();

        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {
            scanResult.getClassesWithAnnotation(TarantoolSpace.class.getName())
                    .loadClasses()
                    .stream()
                    .map(clazz -> clazz.getAnnotation(TarantoolSpace.class))
                    .filter(Objects::nonNull)
                    .map(TarantoolSpace::value)
                    .forEach(tarantoolSpaceNames::add);
        } catch (Exception e) {
            log.error("Error scanning for Tarantool spaces", e);
        }

        return tarantoolSpaceNames;
    }
}
