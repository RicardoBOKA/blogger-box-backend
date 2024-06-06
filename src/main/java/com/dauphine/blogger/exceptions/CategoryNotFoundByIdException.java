package com.dauphine.blogger.exceptions;

import java.util.UUID;

public class CategoryNotFoundByIdException extends Exception{
    public CategoryNotFoundByIdException(UUID id) {
        super("Category not found with the given ID : " + id);
    }
}
