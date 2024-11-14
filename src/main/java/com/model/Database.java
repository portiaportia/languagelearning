package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Database extends DataConstants {

	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		BufferedReader reader = getReaderFromFile(USER_FILE_NAME, USER_FILE_NAME_JUNIT);
		try {
			JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

			for (int i = 0; i < peopleJSON.size(); i++) {
				JSONObject personJSON = (JSONObject) peopleJSON.get(i);
				UUID id = UUID.fromString((String) personJSON.get(USER_ID));
				String userName = (String) personJSON.get(USER_USER_NAME);
				String firstName = (String) personJSON.get(USER_FIRST_NAME);
				String lastName = (String) personJSON.get(USER_LAST_NAME);
				int age = ((Long) personJSON.get(USER_AGE)).intValue();
				String phoneNumber = (String) personJSON.get(USER_PHONE_NUMBER);
	
				users.add(new User(id, userName, firstName, lastName, age, phoneNumber));
			}
			reader.close();
		} catch(Exception e){
			e.printStackTrace();		
		}
		
		return users;
	}
	
	public static ArrayList<Book> getBooks() {
		ArrayList<Book> books = new ArrayList<Book>();

		try {
			BufferedReader reader = getReaderFromFile(BOOK_FILE_NAME, BOOK_FILE_NAME_JUNIT);
			JSONArray booksJSON = (JSONArray) new JSONParser().parse(reader);

			for (int i = 0; i < booksJSON.size(); i++) {
				JSONObject bookJSON = (JSONObject) booksJSON.get(i);
				UUID id = UUID.fromString((String) bookJSON.get(BOOK_ID));
				String title = (String) bookJSON.get(BOOK_TITLE);
				int year = ((Long)bookJSON.get(BOOK_YEAR)).intValue();
				String genre = (String) bookJSON.get(BOOK_GENRE);
				String isbn = (String) bookJSON.get(BOOK_ISBN);
				String publisher = (String) bookJSON.get(BOOK_PUBLISHER);
				String author = (String) bookJSON.get(BOOK_AUTHOR);
				int numCopies = ((Long)bookJSON.get(BOOK_NUM_COPIES)).intValue();
				boolean newArrival = (Boolean)bookJSON.get(BOOK_NEW_ARRIVAL);
				String imageName = (String) bookJSON.get(BOOK_IMG);
				Book book = new Book( id,  title, year,  genre,  isbn,  publisher,  author,  numCopies,  newArrival, imageName);
				ArrayList<Loan> loans = getLoans(book, (JSONArray)bookJSON.get(BOOK_LOANS));
				book.setLoans(loans);
				books.add(book);
			}
			reader.close();
		} catch(Exception e){
			e.printStackTrace();
		}

		return books;
	}

	private static ArrayList<Loan> getLoans(Book book, JSONArray loansJSON){
		ArrayList<Loan> loans = new ArrayList<>();

		for (int i = 0; i < loansJSON.size(); i++) {
			JSONObject loanJSON = (JSONObject) loansJSON.get(i);
			UUID id = UUID.fromString((String) loanJSON.get(LOAN_USER_ID));
			User user = Users.getInstance().getUserById(id);
			if(user == null) continue;
			LocalDate dueDate = getDate((String)loanJSON.get(LOAN_DUE));
			int renewCount = ((Long)loanJSON.get(LOAN_RENEW_COUNT)).intValue();
			Loan loan = new Loan(user, book, dueDate, renewCount);
			loans.add(loan);
			user.addLoan(loan);
		}
		return loans;
	}

	public static void saveUsers() {

		Users users = Users.getInstance();
		ArrayList<User> userList = users.getUsers();
		JSONArray jsonUsers = new JSONArray();

		// creating all the json objects
		for (int i = 0; i < userList.size(); i++) {
			jsonUsers.add(getUserJSON(userList.get(i)));
		}

		// Write JSON file
		try  {
			
			String path = getFileWritingPath(USER_FILE_NAME, USER_FILE_NAME_JUNIT);
			FileWriter writer = new FileWriter(path);
			
			writer.write(jsonUsers.toJSONString());
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static JSONObject getUserJSON(User user) {
		JSONObject userDetails = new JSONObject();
		userDetails.put(USER_ID, user.getId().toString());
		userDetails.put(USER_USER_NAME, user.getUserName());
		userDetails.put(USER_FIRST_NAME, user.getFirstName());
		userDetails.put(USER_LAST_NAME, user.getLastName());
		userDetails.put(USER_AGE, user.getAge());
		userDetails.put(USER_PHONE_NUMBER, user.getPhoneNumber());

		return userDetails;

	}

	private static LocalDate getDate(String data) {
		return LocalDate.parse(data);
	}

	private static String getFileWritingPath(String PATH_NAME, String JUNIT_PATH_NAME) {
		try {
			if(isJUnitTest()){
				URI url = Database.class.getResource(JUNIT_PATH_NAME).toURI();
				return url.getPath();
			} else {
				return PATH_NAME;
			}
		} catch(Exception e){
			System.out.println("Difficulty getting resource path");
			return "";
		}
	}

	private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = Database.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}
}
