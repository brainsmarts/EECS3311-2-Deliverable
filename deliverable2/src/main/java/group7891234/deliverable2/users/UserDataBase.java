package group7891234.deliverable2.users;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase {
	private static UserDataBase instance;
	private List<User> users;
	
	private UserDataBase() {
		users = new ArrayList<>();
		//populate users with actual user data
		Populate();
	}
	
	private void Populate() {
		//read the csv file and populate the database
	}
	
	public static UserDataBase getInstance() {
		if(instance == null) {
			synchronized(UserDataBase.class) {
				if(instance == null) {
					instance = new UserDataBase();
				}
			}
		}
		return instance;
	}
	
	public boolean Login(String username, String password) {
		for (User user : users) {
			if(user.CorrectLogin(username, password)) {
				return true;
			}
		}
		return false;
	}
}
