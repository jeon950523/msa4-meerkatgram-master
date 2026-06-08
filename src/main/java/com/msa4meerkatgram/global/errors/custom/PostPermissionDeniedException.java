package com.msa4meerkatgram.global.errors.custom;

public class PostPermissionDeniedException extends RuntimeException{
    public PostPermissionDeniedException(String message){
        super(message);
    }
}
