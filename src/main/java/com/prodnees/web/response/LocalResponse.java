package com.prodnees.web.response;

import com.prodnees.config.constants.APIErrors;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalResponse {
    private LocalResponse() {
    }

    private static final String DATETIME = "datetime";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_CODE = "messageCode";

    public static ResponseEntity<?> configure(Object object, String message) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, message);
        objectMap.put(MESSAGE_CODE, APIErrors.SUCCESS.getCode());
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);
    }

    public static ResponseEntity<?> configure() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(DATETIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        objectMap.put(ERROR, false);
        objectMap.put(MESSAGE, APIErrors.SUCCESS.getMessage());
        objectMap.put(MESSAGE_CODE, APIErrors.SUCCESS.getCode());
        return ResponseEntity.ok()
                .body(objectMap);
    }

    public static ResponseEntity<?> configure(Object object, String message, int code) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, message);
        objectMap.put(MESSAGE_CODE, code);
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);

    }

    public static ResponseEntity<?> configure(Object object) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, APIErrors.SUCCESS.getMessage());
        objectMap.put(MESSAGE_CODE, APIErrors.SUCCESS.getCode());
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);

    }

    public static ResponseEntity<?> configure(Object object, APIErrors constants) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, constants.getMessage());
        objectMap.put(MESSAGE_CODE, constants.getCode());
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);

    }

    private static void addObjectToResponse(Object object, Map<String, Object> objectMap) {
        objectMap.put(DATETIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        objectMap.put(ERROR, false);
        List<Object> objectList = new ArrayList<>();

        if (object instanceof ArrayList && !((ArrayList) object).isEmpty()) {
            objectList.addAll(((ArrayList<?>) object));
            objectMap.put(((ArrayList<?>) object).get(0).getClass().getSimpleName() + "s", objectList);
        } else if (object instanceof ArrayList && ((ArrayList) object).isEmpty()) {
            objectMap.put(MESSAGE, "no data found");
            objectMap.put("data", "none");
        } else if (object instanceof String) {
            objectMap.put(MESSAGE, object);
        } else {
            objectList.add(object);
            objectMap.put(object.getClass().getSimpleName() + "s", objectList);
        }

    }

}
