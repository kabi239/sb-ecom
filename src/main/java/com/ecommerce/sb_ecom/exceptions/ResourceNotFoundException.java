package com.ecommerce.sb_ecom.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found in %s : %s", resourceName, field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found in %s : %s", resourceName, field,fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

}
// super(String.format("%s not found in %s : %s", resourceName, field,fieldName));
// String.format is a method in Java that returns a formatted string using
// the specified format string and arguments.
//The format string here is "%s not found in %s : %s", which has three
// placeholders (%s) for string values.
//The method takes the format string and the arguments resourceName,
// field, and fieldName to replace the placeholders.
// super(...):
//The super keyword in Java is used to call a constructor of the parent
// class (Exception in this case).
//super(String.format(...)) calls the constructor of the Exception class
// with the formatted string as the message.