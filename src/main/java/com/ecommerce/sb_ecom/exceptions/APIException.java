package com.ecommerce.sb_ecom.exceptions;

public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIException() {
    }
    public APIException(String message) {
        super(message);
    }
}
// 1) public class APIException extends RuntimeException
// public class named APIException that extends RuntimeException, making it a custom unchecked exception.
// Unchecked exceptions do not need to be declared in a method's throws clause.
// 2) private static final long serialVersionUID = 1L;
// If no match is found, an InvalidClassException is thrown.
// Including serialVersionUID helps with the serialization process
// by providing a consistent identifier.
// 3) public APIException(String message) {
//        super(message);
//    }
// This constructor takes a String message as a parameter and passes
// it to the superclass (RuntimeException) constructor.
// This allows you  to create an APIException with a custom error message.