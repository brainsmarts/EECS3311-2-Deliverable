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
import group7891234.deliverable2.users.factory.UserType;

public abstract class User {
	protected UserType type;
	private String username;
	private String password;
	private String email;
	private Map<LocalDate, Set<String>> booksBorrowed;
	private Map<LocalDate, Set<String>> itemsRented;
	private List<String> subscribed;
	private int lostBookCount = 0;
	
	protected User(String username, String password, String email){
		this.username = username; 
		this.password = password;
		this.email = email;
		booksBorrowed = new HashMap<>();
		itemsRented = new HashMap<>();
	}
	
	public String getUserName() {
		return username;
	}
	
	public Map<LocalDate, Set<String>> getBooksBorrowed() {
		return booksBorrowed;
	}
	
	public List<String> getBooksBorrowedList(){
        List<String> allStrings = new ArrayList<>();
        for (Set<String> strings : booksBorrowed.values()) {
            allStrings.addAll(strings);
        }
        return allStrings;
	}

	public Map<LocalDate, Set<String>> getItemsRented() {
		return itemsRented;
	}

	public List<String> getSubscribed() {
		return this.subscribed;
	}

	public int getLostBookCount() {
		return lostBookCount;
	}

	public UserType getType() {
		return type;
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
	
	public boolean addItem(Item item) {
		//if date already exists, then simply add to the list
		if(numOfItems() >= 10) {
			return false;
		}
		LocalDate currentDate = LocalDate.now();
		if(booksBorrowed.get(currentDate) == null) {
			booksBorrowed.put(currentDate,new HashSet<>());
		}
		
		booksBorrowed.get(currentDate).add(item.getId());
        
        return true;
	}
	
	private int numOfItems() {
		int counter = 0;
		for(Set<String> set: itemsRented.values()) {
			counter += set.size();
		}
		return counter;
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

	public void setSubscribed(Set<String> set) {
		this.subscribed = new ArrayList<>(set);
	}
}
