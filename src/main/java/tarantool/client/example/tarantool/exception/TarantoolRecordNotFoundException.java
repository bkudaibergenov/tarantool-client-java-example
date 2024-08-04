package tarantool.client.example.tarantool.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class TarantoolRecordNotFoundException extends RuntimeException {

    public TarantoolRecordNotFoundException(@NonNull String message) {
        super(message);
    }
}
