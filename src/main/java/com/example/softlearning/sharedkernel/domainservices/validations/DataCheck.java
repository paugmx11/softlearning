package com.example.softlearning.sharedkernel.domainservices.validations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase con métodos estáticos para validar datos
 */
public class DataCheck {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Valida que un String no sea null ni esté vacío
     */
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Valida un email básico
     */
    public static boolean isValidEmail(String email) {
        if (!isValidString(email)) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Valida un número positivo
     */
    public static boolean isPositive(int number) {
        return number > 0;
    }
    
    /**
     * Valida un número no negativo
     */
    public static boolean isNotNegative(int number) {
        return number >= 0;
    }
    
    /**
     * Valida un precio positivo
     */
    public static boolean isValidPrice(double price) {
        return price > 0;
    }
    
    /**
     * Valida y parsea una fecha en formato dd/MM/yyyy
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        if (!isValidString(dateStr)) return null;
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    /**
     * Formatea una fecha a String dd/MM/yyyy
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Valida un ISBN básico (10 o 13 dígitos)
     */
    public static boolean isValidISBN(String isbn) {
        if (!isValidString(isbn)) return false;
        String clean = isbn.replaceAll("[^0-9X]", "");
        return clean.length() == 10 || clean.length() == 13;
    }
}