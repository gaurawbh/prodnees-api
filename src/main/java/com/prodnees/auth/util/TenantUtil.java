package com.prodnees.auth.util;

public class TenantUtil {
    public static final String MASTER_SCHEMA = "prodnees_auth";
    public static final String VALID_SCHEMA_PATTERN = "^[a-zA-Z0-9_]+$";
    public static final String USE_MASTER_SCHEMA = "use " + MASTER_SCHEMA;

    public static String newSchema(int companyId) {
        return String.format("prod_%s", companyId);
    }

}
