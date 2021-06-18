package com.prodnees.web.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.web.response.ErrorResponse;
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
import java.util.Objects;
import java.util.Optional;

import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;

@ControllerAdvice
@CrossOrigin
public final class GlobalExceptionController {
    private static final String FOREIGN_KEY_CONSTRAINT_ERROR = "cannot delete or update a parent row: a foreign key constraint fails";

    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResponseEntity<?> exception(MissingServletRequestPartException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NeesAccessDeniedException.class)
    public ResponseEntity<?> exception(NeesAccessDeniedException e) {
        return new ResponseEntity<>(new ErrorResponse(ACCESS_DENIED), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<?> exception(MultipartException multipartException, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse(multipartException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesBadCredentialException.class)
    public ResponseEntity<?> exception(NeesBadCredentialException e) {
        return new ResponseEntity<>(new ErrorResponse("incorrect password"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NeesInvalidJwtTokenException.class)
    public ResponseEntity<?> exception(NeesInvalidJwtTokenException e) {
        return new ResponseEntity<>(new ErrorResponse("invalid or missing token"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> exception(MissingServletRequestParameterException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesUnknownException.class)
    public ResponseEntity<?> exception(NeesUnknownException e) {
        return new ResponseEntity<>(new ErrorResponse("unknown error had occurred"), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(new ErrorResponse(stringBuilder.toString()), HttpStatus.PRECONDITION_FAILED);
    }


    @ExceptionHandler(NeesNotFoundException.class)
    public ResponseEntity<?> exception(NeesNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NeesBadRequestException.class)
    public ResponseEntity<?> exception(NeesBadRequestException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NeesForbiddenException.class)
    public ResponseEntity<?> exception(NeesForbiddenException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getCode()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<?> exception(HttpServerErrorException.InternalServerError e) {
        return new ResponseEntity<>(new ErrorResponse("unexpected error occurred"), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> exception(NoHandlerFoundException e, HttpServletRequest httpServletRequest) {
        e.getRequestURL();
        httpServletRequest.getServerName(); //localhost
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);

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
        return new ResponseEntity<>(new ErrorResponse(errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NeesInvalidRequestBodyException.class)
    public ResponseEntity<?> exception(NeesInvalidRequestBodyException e) {
        return new ResponseEntity<>(new ErrorResponse(APIErrors.INVALID_REQUEST_BODY), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NeesInfoException.class)
    public ResponseEntity<?> exception(NeesInfoException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler({SQLException.class,})
    public ResponseEntity<?> exception(SQLException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> exception(SQLIntegrityConstraintViolationException e) {

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> exception(DataIntegrityViolationException e) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException) {
            Throwable throwable = e.getRootCause();
            Optional<String> exMessage = Optional.ofNullable(throwable.getMessage());
            exMessage.ifPresentOrElse(s -> {
                if (s.toLowerCase().contains(FOREIGN_KEY_CONSTRAINT_ERROR)) {
                    errorMessageBuilder.append("object cannot be deleted, the object is in use(referenced by another object)");
                } else {
                    errorMessageBuilder.append(s);
                }
            }, () -> errorMessageBuilder.append(Objects.requireNonNull(e.getRootCause()).getMessage()));
        }

        return new ResponseEntity<>(new ErrorResponse(
                errorMessageBuilder.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> exception(MethodArgumentNotValidException e) {
        StringBuilder errorStringBuilder = new StringBuilder();
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        objectErrorList.forEach(objectError -> {
            String error = objectError.getDefaultMessage();
            errorStringBuilder.append(error).append(". ");
        });

        return new ResponseEntity<>(new ErrorResponse(errorStringBuilder.toString()), HttpStatus.BAD_REQUEST);
    }


}
