package tarantool.client.example.tarantool.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to mark a class as a representation of a Tarantool space.
 * The annotated class should represent a space in the Tarantool database.
 * The value of the annotation specifies the name of the space.
 * <p>
 * @author Bekzhan Kudaibergenov
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TarantoolSpace {

    /**
     * Specifies the name of the Tarantool space that the annotated class represents.
     *
     * @return the name of the Tarantool space.
     */
    String value();

}
