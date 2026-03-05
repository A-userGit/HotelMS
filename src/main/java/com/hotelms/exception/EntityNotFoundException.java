package com.hotelms.exception;

import com.hotelms.enums.ContactType;

import java.util.List;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException contactsByTypeNotFound(String searchEntity, String searchEntityValue, List<ContactType> contactTypes) {
        String format = String.format("For %s with value %s contacts with types %s are missing", searchEntity, searchEntityValue, contactTypes);
        return new EntityNotFoundException(format);
    }

    public static EntityNotFoundException entityNotFound(String searchEntity, String searchField, String searchEntityValue) {
        String format = String.format("Entity %s with value of %s = %s is not found", searchEntity, searchField, searchEntityValue);
        return new EntityNotFoundException(format);
    }
}
