package tarantool.client.example.tarantool.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class TarantoolClientException extends RuntimeException {

    public TarantoolClientException(@NonNull String message) {
        super(message);
    }
}
