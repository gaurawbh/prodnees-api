/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.util;

import com.prodnees.config.constants.APIErrors;
import com.prodnees.web.exception.NeesBadRequestException;
import org.springframework.util.Assert;

public class LocalAssert extends Assert {

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean expression, APIErrors apiErrors) {
        if (expression) {
            throw new NeesBadRequestException(apiErrors);
        }
    }

    public static void isTrue(boolean expression, APIErrors apiErrors) {
        if (!expression) {
            throw new NeesBadRequestException(apiErrors);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

}
