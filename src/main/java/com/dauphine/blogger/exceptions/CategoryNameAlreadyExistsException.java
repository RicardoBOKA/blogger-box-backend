package com.dauphine.blogger.exceptions;

import java.util.UUID;

public class CategoryNameAlreadyExistsException extends Exception{
    public CategoryNameAlreadyExistsException(String name) {
        super("Category " + name + " already exists, or similar.");
    }
}
