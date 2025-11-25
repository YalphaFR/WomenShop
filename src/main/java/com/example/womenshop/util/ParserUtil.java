package com.example.womenshop.util;

public class ParserUtil {

    public static Integer parseIntOrNull(String s) {
        return (s == null || s.isBlank()) ? 0 : Integer.parseInt(s);
    }

    public static Double parseDoubleOrNull(String s) {
        return (s == null || s.isBlank()) ? 0 : Double.parseDouble(s);
    }
}

