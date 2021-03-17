package com.prodnees.web.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.web.response.GlobalResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;

@ControllerAdvice
@CrossOrigin
public final class GlobalExceptionController {


    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResponseEntity<?> exception(MissingServletRequestPartException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                e.getMessage(), 99), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NeesAccessDeniedException.class)
    public ResponseEntity<?> exception(NeesAccessDeniedException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                ACCESS_DENIED.getMessage(), ACCESS_DENIED.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<?> exception(MultipartException multipartException, HttpServletRequest request) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                multipartException.getMessage(), 99), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesBadCredentialException.class)
    public ResponseEntity<?> exception(NeesBadCredentialException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.UNAUTHORIZED, true,
                "incorrect password", 99), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NeesInvalidJwtTokenException.class)
    public ResponseEntity<?> exception(NeesInvalidJwtTokenException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.UNAUTHORIZED, true,
                "invalid or missing token", 99), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> exception(MissingServletRequestParameterException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                e.getMessage(), 99), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesUnknownException.class)
    public ResponseEntity<?> exception(NeesUnknownException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                "unknown error had occurred", 99), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> exception(HttpMessageNotReadableException e) {
        StringBuilder stringBuilder = new StringBuilder();
        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException exception = (InvalidFormatException) e.getCause();
            String[] messageParts = exception.getOriginalMessage().split(":");
            stringBuilder.append(exception.getValue())
                    .append(": ")
                    .append(messageParts[messageParts.length - 1])
                    .append(messageParts[messageParts.length - 2]);

        } else if (e.getLocalizedMessage().contains("com.neesum.userprofile.domain.UserType.UserType")) {
            stringBuilder.append("invalid userType");
        } else {
            stringBuilder.append(APIErrors.INVALID_REQUEST_BODY.getMessage());
        }
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.PRECONDITION_FAILED, true,
                stringBuilder.toString(), 99), HttpStatus.PRECONDITION_FAILED);
    }


    @ExceptionHandler(NeesNotFoundException.class)
    public ResponseEntity<?> exception(NeesNotFoundException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.NOT_FOUND, true,
                e.getMessage(), e.getCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NeesBadRequestException.class)
    public ResponseEntity<?> exception(NeesBadRequestException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                e.getMessage(), e.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesForbiddenException.class)
    public ResponseEntity<?> exception(NeesForbiddenException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.FORBIDDEN, true,
                e.getMessage(), e.getCode()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<?> exception(HttpServerErrorException.InternalServerError e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                "unexpected error occurred", 99), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                e.getLocalizedMessage(), 99), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> exception(NoHandlerFoundException e, HttpServletRequest httpServletRequest) {
        e.getRequestURL();
        httpServletRequest.getServerName(); //localhost
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                e.getLocalizedMessage(), 99), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exception(RuntimeException e) {
        StringBuilder errorMessage = new StringBuilder();
        try {
            if (((ConstraintViolationException) e).getConstraintViolations() != null) {
                ((ConstraintViolationException) e)
                        .getConstraintViolations()
                        .forEach(constraintViolation -> errorMessage.append(constraintViolation.getMessageTemplate()).append(". "));
            } else {
                errorMessage.append(e.getLocalizedMessage());
            }
        } catch (Exception ex) {
            errorMessage.append(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                errorMessage.toString(), 99), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesTeapotException.class)
    public ResponseEntity<?> exception(NeesTeapotException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.I_AM_A_TEAPOT, true,
                "i am a coffee pot disguised as a tea pot. sshhhh!!!", 99), HttpStatus.I_AM_A_TEAPOT);
    }


    @ExceptionHandler(NeesInvalidRequestBodyException.class)
    public ResponseEntity<?> exception(NeesInvalidRequestBodyException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                APIErrors.INVALID_REQUEST_BODY.getMessage(),
                APIErrors.INVALID_REQUEST_BODY.getCode()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NeesInfoException.class)
    public ResponseEntity<?> exception(NeesInfoException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.ACCEPTED, true,
                e.getMessage(), e.getCode()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler({SQLException.class,})
    public ResponseEntity<?> exception(SQLException e) {
        return new ResponseEntity<>(new GlobalResponse(HttpStatus.ACCEPTED, true,
                e.getMessage(), 99), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> exception(SQLIntegrityConstraintViolationException e) {

        return new ResponseEntity<>(new GlobalResponse(HttpStatus.ACCEPTED, true,
                e.getMessage(), 99), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> exception(DataIntegrityViolationException e) {
        if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException exception = (SQLIntegrityConstraintViolationException) e.getRootCause();
            return new ResponseEntity<>(new GlobalResponse(HttpStatus.ACCEPTED, true,
                    exception.getMessage(), 99), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(new GlobalResponse(HttpStatus.ACCEPTED, true,
                    e.getRootCause().getMessage(), 99), HttpStatus.ACCEPTED);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> exception(MethodArgumentNotValidException e) {
        StringBuilder errorStringBuilder = new StringBuilder();
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        objectErrorList.forEach(objectError -> {
            String error = objectError.getDefaultMessage();
            errorStringBuilder.append(error).append(". ");
        });

        return new ResponseEntity<>(new GlobalResponse(HttpStatus.BAD_REQUEST, true,
                errorStringBuilder.toString(), 99), HttpStatus.BAD_REQUEST);
    }


}
