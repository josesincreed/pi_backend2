package com.example.pib2.helpers;

public enum TechnicalErrorMessage {

    // ==== Categoría ====
    CATEGORY_NOT_FOUND("ERR001", "La categoría con ID %s no existe"),

    // ==== Producto ====
    PRODUCT_NOT_FOUND("ERR002", "El producto con ID %s no existe"),
    PRODUCT_CATEGORY_INVALID("ERR003", "La categoría asignada al producto con ID %s no es válida"),

    // ==== Usuario ====
    USER_NOT_FOUND("ERR004", "El usuario con ID %s no existe"),
    USER_ALREADY_EXISTS("ERR005", "El usuario con el correo %s ya existe"),

    // ==== Orden ====
    ORDER_NOT_FOUND("ERR006", "La orden con ID %s no existe"),
    ORDER_EMPTY("ERR007", "La orden no puede estar vacía"),

    // ==== Genéricos ====
    INTERNAL_SERVER_ERROR("ERR500", "Ha ocurrido un error interno en el servidor"),
    INVALID_REQUEST("ERR400", "La petición no es válida");

    private final String code;
    private final String message;

    TechnicalErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
