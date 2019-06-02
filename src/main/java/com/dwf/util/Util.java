package com.dwf.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {

    public static String stackTraceToString(Throwable e) {
        if (e != null) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            return errors.toString();
        }
        return "";
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("+00:00"));
    }

}
