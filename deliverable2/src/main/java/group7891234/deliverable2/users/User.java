package group7891234.deliverable2.users;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.NewsLetters;

public abstract class User {
	String username;
	String password;
	String email;
	List<Book> borrowing = null;
	Map<Date, List<Book>> booksBorrowed;
	Map<Date, List<Item>> itemsBorrowed;
	List<NewsLetters> subscribed = null;
	int lostBookCount;
	protected User(String username, String password, String email){
		
	}
	
	protected boolean CorrectLogin(String username, String password) {
		return (username.compareTo(this.username) == 0 && password.compareTo(this.password) == 0);
	}
	
	public void Subscribe(NewsLetters newsletter) {
		subscribed.add(newsletter);
	}
	
	public void UnSubscribe(NewsLetters newsletter) {
		subscribed.remove(newsletter);
	}
}
