package group7891234.deliverable2.users;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.NewsLetter;

public abstract class User {
	private String username;
	private String password;
	private String email;
	private List<Book> borrowing;
	private Map<LocalDate, Set<String>> booksBorrowed;
	private Map<LocalDate, Set<String>> itemsRented;
	private List<String> subscribed;
	private int lostBookCount;
	
	protected User(String username, String password, String email){
		this.username = username; 
		this.password = password;
		this.email = email;
		booksBorrowed = new HashMap<>();
		itemsRented = new HashMap<>();
	}
	
	protected boolean CorrectLogin(String username, String password) {
		return (username.compareTo(this.username) == 0 && password.compareTo(this.password) == 0);
	}
	
	public void Subscribe(NewsLetter newsletter) {
		subscribed.add(newsletter.getId());
	}
	
	public void UnSubscribe(NewsLetter newsletter) {
		subscribed.remove(newsletter.getId());
	}
	
	public void borrow(Book book) {
		//if date already exists, then simply add to the list
		LocalDate currentDate = LocalDate.now();
        booksBorrowed.computeIfAbsent(currentDate, k -> new HashSet<>()).add(book.getId());
	}
	
	public void addItem(Item item) {
		//if date already exists, then simply add to the list
		LocalDate currentDate = LocalDate.now();
        itemsRented.computeIfAbsent(currentDate, k -> new HashSet<>()).add(item.getId());
	}
	
	public String getEmail() {
		return email;
	}
	
	
	protected void setBorrowed(Map<LocalDate, Set<String>> borrowed) {
		this.booksBorrowed = borrowed;
	}
	
	protected void setRenting(Map<LocalDate, Set<String>> renting) {
		this.itemsRented = renting;
	}
	
	public void accepts(UserVisitor visitor) {
		
	}
}
