/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.domain;

public enum ApplicationRole {
    //appOwner has full rights including withdrawing a user from sysAdmin
    appOwner,
    //sysAdmin can give users a sysAdmin or an admin ApplicationRight.
    //sysAdmin can also give CRUD rights to an admin
    sysAdmin,
    admin,//cannot issue ApplicationRight to any users
}
