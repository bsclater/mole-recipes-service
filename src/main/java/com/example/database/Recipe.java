package com.example.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

	@Entity
	public class Recipe {

		@Id
		@GeneratedValue
	    private long id;
	 
	    @Column(nullable = false, unique = true)
	    private String name;
	 
	    @Column(nullable = false)
	    private String method;
	    
	    @Column
	    @OneToMany(
	    	cascade = CascadeType.ALL,
	    	fetch = FetchType.EAGER
	    )
	    private List<Ingredient> ingredients;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}
	    
		public List<Ingredient> getIngredients() {
			return ingredients;
		}

		public void setIngredients(List<Ingredient> ingredients) {
			this.ingredients = ingredients;
		}
	    
	}

