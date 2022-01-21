package com.eduardo.clientcrud.service.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String msg){
        super(msg);
    }
}
