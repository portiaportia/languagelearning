package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Book {
	private UUID id;
	private String title;
	private int year;
	private String genre;
	private String isbn;
	private String publisher;
	private String author;
	private int numCopies;
	private boolean newArrival;
	private String imageName;
	private ArrayList<Loan> loans;
	
	public Book(UUID id, String title, int year, String genre, String isbn, String publisher, String author, int numCopies, boolean newArrival,String imageName) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.isbn = isbn;
		this.publisher = publisher;
		this.author = author;
		this.numCopies = numCopies;
		this.newArrival = newArrival;
		this.imageName = imageName;
	}

	public void setLoans(ArrayList<Loan> loans){
		this.loans = loans;
	}
	
	public String getTitle() {
		return title;
	}

	public int getYear(){
		return year;
	}

	public String getGenre() {
		return genre;
	}

	public String getISBN() {
		return isbn;
	}

	public String getPublisher(){
		return publisher;
	}
	
	public String getAuthor() {
		return author;
	}

	public int getNumCopies(){
		return numCopies;
	}

	public int getNumAvailableCopies(){
		return numCopies - loans.size();
	}

	public boolean isNewArrival(){
		return newArrival;
	}
        
	public String getImageName(){
		return imageName;
	}

	public ArrayList<Loan> getLoans(){
		return loans;
	}

	public String toString(){
		return title;
	}
}
