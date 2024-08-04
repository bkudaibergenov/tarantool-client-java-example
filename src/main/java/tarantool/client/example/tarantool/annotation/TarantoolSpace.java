package tarantool.client.example.tarantool.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TarantoolSpace {

    String value();

}
