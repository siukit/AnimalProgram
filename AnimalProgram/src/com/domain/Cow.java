package com.domain;
import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Table;

public class Cow extends Animal implements Serializable{

	
	public Cow(String owner, String age) {
		// TODO Auto-generated constructor stub
		super(owner, "Cow", age);
	    
	}
	
}
