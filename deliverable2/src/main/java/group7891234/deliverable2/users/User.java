package group7891234.deliverable2.users;

import java.util.List;

public abstract class User {
	String username;
	String password;
	String email;
	List<Book> borrowing = null;
	List<Item> renting = null;
	List<NewsLetter> subscribed = null;
	int lostBookCount;
	
	public boolean correctLogin(String username, String password) {
		return (username.compareTo(this.username) == 0 && password.compareTo(this.password) == 0);
	}
	
}
