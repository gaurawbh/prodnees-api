package com.prodnees.core.web.response;

import com.prodnees.core.config.constants.APIErrors;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalResponse {
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";

    private LocalResponse() {
    }

    public static ResponseEntity<?> configure(Object object, String message) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, message);
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);
    }

    public static ResponseEntity<?> configure() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(ERROR, false);
        objectMap.put(MESSAGE, APIErrors.SUCCESS.getMessage());
        return ResponseEntity.ok()
                .body(objectMap);
    }

    public static ResponseEntity<?> configure(Object object) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, APIErrors.SUCCESS.getMessage());
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);

    }

    public static ResponseEntity<?> configure(Object object, APIErrors constants) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(MESSAGE, constants.getMessage());
        addObjectToResponse(object, objectMap);
        return ResponseEntity.ok()
                .body(objectMap);

    }

    private static void addObjectToResponse(Object object, Map<String, Object> objectMap) {
        objectMap.put(ERROR, false);
        List<Object> objectList = new ArrayList<>();

        if (object instanceof ArrayList && !((ArrayList) object).isEmpty()) {
            objectList.addAll(((ArrayList<?>) object));
            objectMap.put(DATA, objectList);
        } else if (object instanceof ArrayList && ((ArrayList) object).isEmpty()) {
            objectMap.put(MESSAGE, "no data found");
            objectMap.put(DATA, "none");
        } else if (object instanceof String) {
            objectMap.put(MESSAGE, object);
        } else {
            objectMap.put(DATA, object);
        }

    }

}
