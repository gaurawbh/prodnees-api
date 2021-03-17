/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.web.exception;

public class NeesRunnableExceptions {
    private NeesRunnableExceptions() {
    }

    public static Runnable handleNotFound() {
        throw new NeesNotFoundException();
    }
}
