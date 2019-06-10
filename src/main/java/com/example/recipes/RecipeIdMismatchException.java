package com.example.recipes;

public class RecipeIdMismatchException extends RuntimeException {

	 public RecipeIdMismatchException() {
	        super();
	    }
	
    public RecipeIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}