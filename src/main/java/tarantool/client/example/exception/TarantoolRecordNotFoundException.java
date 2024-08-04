package tarantool.client.example.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class TarantoolRecordNotFoundException extends RuntimeException {

    public TarantoolRecordNotFoundException(@NonNull String message) {
        super(message);
    }
}
