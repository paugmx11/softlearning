package com.example.softlearning.sharedkernel.model.exceptions;

public class BuildException extends Exception {
    
    public BuildException(String error) {
        super(error);
    }
    
    public BuildException(String error, Throwable cause) {
        super(error, cause);
    }
}