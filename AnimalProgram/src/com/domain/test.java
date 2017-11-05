package com.domain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class test implements Serializable{

	//animals is the arraylist that stores the animals which will be serialized and deserialized from the harddrive
	static ArrayList<Animal> animals = new ArrayList<Animal>(100);
	static Scanner scanner = new Scanner(System.in);
	
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	static int option, selectedType;
	static String owner = null;
	static String age = null;
	
	static Configuration cfg;
	static SessionFactory factory;
	static Session session;
	static Transaction tx;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//activate Hibernate framework
		cfg = new Configuration();
		//read mapping cfg data
		cfg = cfg.configure("/com/cfgs/hibernate.cfg.xml");
		//build section factory object
		factory = cfg.buildSessionFactory();
		//open session with db
        session = factory.openSession();	
        
		System.out.println("Select option: \n (1) Store new animal \n (2) Show all stored animals");

		//asks user to select an option
		while (true){
			if(scanner.hasNextInt()) {
			    option = scanner.nextInt();
			    break;
			}else {
				System.out.println("Select avaialble options only");
			}
		}
		
		
		if (option == 1) {
			//run this method if user selected option 1
			storeNewAnimals();
			
		}else if (option == 2) {
			//run this method if user selected option 2
			viewAllAnimals();
		}
		
	}
	
	//deserialize the arraylist from Animals.dat 
	private static void deserializeAnimals(){
	    try {
		        FileInputStream fis = new FileInputStream("Animals.dat");
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        animals = (ArrayList<Animal>) ois.readObject();
		        ois.close();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
	}
	
	// serialize animals arraylist back to the file
	private static void serializeAnimals(){
		try {
	            FileOutputStream fos = new FileOutputStream("Animals.dat");
	            ObjectOutputStream oos = new ObjectOutputStream(fos);
	            oos.writeObject(animals);
	            oos.close();
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
	}
	
	static void storeNewAnimals() {
		
		System.out.println("Select animal type: \n (1) Cow \n (2) Goat \n (3) Mountain Goat");
		
		if(scanner.hasNextInt()) {
		    selectedType = scanner.nextInt();
		    
		   // Cow option used Hibernate to demonstrate creating object to the database
		  switch (selectedType) {
		  case 1: 
			  System.out.println("Enter owner's name: ");
			  Scanner scanner2 = new Scanner(System.in);
			  owner = scanner2.nextLine();
			  System.out.println("Enter it's age: ");
			  Scanner scanner5 = new Scanner(System.in);
			  age = scanner5.nextLine();
			  
			  Animal newCow = new Animal(owner, "Cow", age);
			  animals.add(newCow);
			  serializeAnimals();

			  
			  System.out.println("This animal is now being stored in the database");
			  
//			  String sql = "INSERT INTO animals " +
//	                   "VALUES (0, '" + owner + "', 'Cow', '" + age+ "');";
			  
			  //use hibernate store object in database 
			  try{
				  tx = session.beginTransaction();
				  session.save(newCow);
				  tx.commit();
			  }catch(Exception e){
				  tx.rollback();
			  }
			  System.out.println(newCow.getAge() + " " + newCow.getOwner());
			  
		      session.close();
			  
//			  try {
//				con = DbConnectionManager.getConnection();
//			
//				stmt = con.createStatement();
//				stmt.executeUpdate(sql);
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
////			}
			 
			  break;
			  
		  case 2:
			  System.out.println("Enter owner's name: ");
			  Scanner scanner7 = new Scanner(System.in);
			  owner = scanner7.nextLine();
			  System.out.println("Enter it's age: ");
			  Scanner scanner6 = new Scanner(System.in);
			  age = scanner6.nextLine();
			  
			  animals.add(new Goat(owner, age));
			  serializeAnimals();
//			  System.out.println(animals.get(animals.size()-1).getOwner());
			  System.out.println("This animal is now being stored in the database");
			  
			  //SQL command to insert object to the db table
			  String sql2 = "INSERT INTO animals " +
	                   "VALUES (0, '" + owner + "', 'Goat', '" + age+ "');";
			  
			  //set up connection and execute sql command
			  try {
				con = DbConnectionManager.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(sql2);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  break;
			  
		  case 3:
			  
			  System.out.println("Enter owner's name: ");
			  Scanner scanner8 = new Scanner(System.in);
			  owner = scanner8.nextLine();
			  System.out.println("Enter it's age: ");
			  Scanner scanner9 = new Scanner(System.in);
			  age = scanner9.nextLine();
			  
			  animals.add(new MountainGoat(owner, age));
			  serializeAnimals();
//			  System.out.println(animals.get(animals.size()-1).getOwner());
			  System.out.println("This animal is now being stored in the database");
			  
			  String sql3 = "INSERT INTO animals " +
	                   "VALUES (0, '" + owner + "', 'Mountain Goat', '" + age+ "');";
			  
			  try {
				con = DbConnectionManager.getConnection();
			
				stmt = con.createStatement();
				stmt.executeUpdate(sql3);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  break;
		  }
		}
		System.out.println("\nSelect option: \n (1) View all stored animals \n (2) Exit program");
		Scanner scanner3 = new Scanner(System.in);
		if (scanner3.nextInt() == 1){
			viewAllAnimals();
		}else{
			System.exit(0);
		}
		
	}
	
	//to view all the animal objects being created
	static void viewAllAnimals() {
		deserializeAnimals();
		System.out.println("List of all the stored animals: ");
		int x = 1;
		//loop through the animals arraylists and show on command line
		for (Animal a : animals) {
			System.out.println("(" + x + ")" + " Type: " + a.getType() + "   Owner: " + a.getOwner() + "   Age: " + a.getAge());
			x++;
        }
		
		System.out.println("\nSelect option: \n (1) Store new animal \n (2) Exit program");
		Scanner scanner4 = new Scanner(System.in);
		if (scanner4.nextInt() == 1){
			storeNewAnimals();
		}else{
			System.exit(0);
		}
	}

}
