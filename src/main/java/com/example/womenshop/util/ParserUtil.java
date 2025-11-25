package com.example.womenshop.util;

public class ParserUtil {

    public static Integer parseIntOrNull(String s) {
        return (s == null || s.isBlank()) ? -1 : Integer.parseInt(s);
    }

    public static Double parseDoubleOrNull(String s) {
        return (s == null || s.isBlank()) ? -1 : Double.parseDouble(s);
    }
}

