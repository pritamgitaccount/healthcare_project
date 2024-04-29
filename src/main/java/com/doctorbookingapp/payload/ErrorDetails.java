package com.doctorbookingapp.payload;

// Assuming Java 16 or later
import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {
    // No need for an explicit constructor or getters in a record
}

    // No need for an explicit constructor or getters in a record

//    private final Date timestamp;
//    private final String message;
//    private final String details;
//
//    //Constructor
//    public ErrorDetails(Date timestamp, String message, String details) {
//        this.timestamp = timestamp;
//        this.message = message;
//        this.details = details;
//    }

