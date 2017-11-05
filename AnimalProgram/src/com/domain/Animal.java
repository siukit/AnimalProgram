package com.domain;
import java.io.Serializable;

import javax.persistence.MappedSuperclass;


public class Animal implements Serializable{
	
	private String owner;
	private String age;
	private String type;
	private int pk;
	
	//constructor
	public Animal(String owner, String type, String age){
	       this.pk = 0;
	       this.owner = owner;
	       this.type = type;
	       this.age = age;
	       
	    }
	
	
	//setter and getter methods for the attributes
	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
