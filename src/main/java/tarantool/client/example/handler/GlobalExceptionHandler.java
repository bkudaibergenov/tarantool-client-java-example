package tarantool.client.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tarantool.client.example.exception.TarantoolClientException;
import tarantool.client.example.exception.TarantoolRecordNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TarantoolRecordNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(TarantoolRecordNotFoundException ex) {
        log.error("Record not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TarantoolClientException.class)
    public ResponseEntity<String> handleTarantoolExampleException(TarantoolClientException ex) {
        log.error("Tarantool client exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
