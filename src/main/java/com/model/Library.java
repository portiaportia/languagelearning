package com.model;

import java.util.ArrayList;

public class Library {
	private Books books;
	private Users users;
	private User currentUser;
        private static Library library;
	
	private Library() {
		books = Books.getInstance();
		users = Users.getInstance();
	}
        
        public static Library getInstance(){
            if(library == null){
                library = new Library();
            }
            
            return library;
        }
	
	//creates a new user account
	public boolean createAccount(String userName, String firstName, String lastName, int age, String phoneNumber)
	{
		return users.addUser(userName,  firstName,  lastName,  age,  phoneNumber);
	}
	
	public boolean login(String userName) {
		if(!users.haveUser(userName))return false;
		
		currentUser = users.getUser(userName);
		return true;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public Book getBook(String bookName) {
		return books.getBook(bookName);
	}
	
	public void logout() {
		users.saveUsers();
	}
}
