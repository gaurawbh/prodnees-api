package com.prodnees.auth.config.tenancy;

import com.prodnees.auth.util.TenantUtil;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String schema = TenantContext.getCurrentTenant();
        if (schema == null) TenantContext.setCurrentTenant(TenantUtil.MASTER_SCHEMA);
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    public static void setTenant(String tenantSchema){
        TenantContext.clear();
        TenantContext.setCurrentTenant(tenantSchema);
    }
}
