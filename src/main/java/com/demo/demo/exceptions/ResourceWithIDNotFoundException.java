package com.demo.demo.exceptions;

public class ResourceWithIDNotFoundException extends RuntimeException {
    public ResourceWithIDNotFoundException(Integer id) {
        super(String.format("Resource with id %d not found", id));
    }
}
