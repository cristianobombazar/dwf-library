package com.dwf.tenant;

import java.util.List;

public class TenantContext {

    private static ThreadLocal<List<String>> currentMultipleTenant = new ThreadLocal<>();

    public static List<String> getCurrentMultipleTenant() {
      return currentMultipleTenant.get();
    }

    public static void setCurrentMultipleTenant(List<String> tenant) {
      currentMultipleTenant.set(tenant);
    }

    public static void clear() {
      currentMultipleTenant.set(null);
    }
}
