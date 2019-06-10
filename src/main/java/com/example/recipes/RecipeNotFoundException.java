package com.example.recipes;

public class RecipeNotFoundException extends RuntimeException {
	 
	 public RecipeNotFoundException() {
	        super();
	    }

	
    public RecipeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    // ...
}
