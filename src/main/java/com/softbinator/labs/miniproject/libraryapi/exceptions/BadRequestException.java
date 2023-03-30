package com.softbinator.labs.miniproject.libraryapi.exceptions;

import java.lang.reflect.Type;
import java.text.MessageFormat;

public class BadRequestException extends RuntimeException {

    //Perhaps could be done better with a map with keys being message and values these constants
    //To be sent to the client depending on what is wrong with the request
    //not necessary, could just send Bad Request

    public final static String INVALID_DATA =
            "Number of copies must be positive and available copies less then equal to total!";
    public final static String INVALID_YEAR = "Year must be positive!";
    public final static String BOOK_UNAVAILABLE = "Book is unavailable to borrow!";
    public final static String BOOK_BORROWED = "Book is already borrowed by user!";
    public final static String AUTHOR_EXISTS = "Author already exists!";
    public static final String BOOK_NOT_BORROWED = "User hasn't borrowed this book";
    public static final String DUPLICATE_BOOK = "Book already exists";

    //In case we don't want to send any details
    public static final String HIDDEN = "";


    private final String message;
    private final String externalMessage;
    private final Type issuedInClass;

    public BadRequestException(String message, String externalMessage, Type issuedInClass) {
        this.message = message;
        this.externalMessage = externalMessage;
        this.issuedInClass = issuedInClass;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Bad Request with message: {0} issued in class: {1}",
                this.message, this.issuedInClass.getTypeName());
    }

    public String getExternalMessage() {
        return MessageFormat.format("Bad Request! {0}",
                this.externalMessage);
    }
}
