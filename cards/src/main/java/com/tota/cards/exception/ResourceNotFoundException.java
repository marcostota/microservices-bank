package com.tota.cards.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with the giver input data %s : '%s'",resourceName,fieldName,fieldValue));
    }
}
