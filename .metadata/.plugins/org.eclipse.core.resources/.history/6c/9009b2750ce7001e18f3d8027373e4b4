package group7891234.deliverable2.users;

import group7891234.deliverable2.users.type.Faculty;
import group7891234.deliverable2.users.type.Manager;
import group7891234.deliverable2.users.type.Non_Faculty;
import group7891234.deliverable2.users.type.Students;
import group7891234.deliverable2.users.type.UserType;
import group7891234.deliverable2.users.type.Visitor;

public class UserFactory {
	
	public static User getUser(String username, String password, String email, UserType type) {
		switch(type) {
		case FACULTY: 
				return new Faculty(username, password, email);
		case NON_FACULTY:
				return new Non_Faculty(username,password,email);
		case MANAGER:
				return new Manager(username,password,email);
		case STUDENT:
				return new Students(username,password,email);
		case VISITOR:
				return new Visitor(username,password,email);
		default:
				
		}
		return null;
		
	}
}
