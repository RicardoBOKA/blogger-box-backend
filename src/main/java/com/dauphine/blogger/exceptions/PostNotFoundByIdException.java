package com.dauphine.blogger.exceptions;

import java.util.UUID;

public class PostNotFoundByIdException extends  Exception{
    public PostNotFoundByIdException(UUID id) {
        super("Post not found with the given ID : " + id);
    }
}
