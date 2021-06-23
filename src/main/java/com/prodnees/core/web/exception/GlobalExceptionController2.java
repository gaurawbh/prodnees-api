package com.prodnees.core.web.exception;

import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController2 {

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<?> exception(ConversionFailedException e) {
        String provided = ValidatorUtil.ifValidStringOrElse(e.getSourceType().getType().getName(), "null");
        String required = e.getTargetType().getType().getName();
        String value = ValidatorUtil.ifValidStringOrElse(e.getValue().toString(), "null");
        String errorMessage = String.format("Conversion failed, required type: %s, but found %s for value: %s", required, provided, value);
        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

}
