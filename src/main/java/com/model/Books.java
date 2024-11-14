package com.model;

import java.util.ArrayList;

public class Books {
	private static Books books;
	private ArrayList<Book> bookList;
	
	private Books() {
		bookList = Database.getBooks();
	}
	
	public static Books getInstance() {
		if(books == null) {
			books = new Books();
		}
		
		return books;
	}
	
	public Book getBook(String bookName) {
		for(Book book : bookList) {
			if(book.getTitle().equalsIgnoreCase(bookName)) {
				return book;
			}
		}
		
		return null;
	}

	public ArrayList<Book> getBooks(){
		return bookList;
	}
}
