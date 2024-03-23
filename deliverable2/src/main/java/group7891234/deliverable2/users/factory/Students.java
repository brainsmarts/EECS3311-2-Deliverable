package group7891234.deliverable2.users.factory;

import java.util.Set;

import group7891234.deliverable2.users.User;

public class Students extends User{
	Set<String> courses;
	
	public Students(String username, String password, String email) {
		super(username, password, email);
		// TODO Auto-generated constructor stub
	}
	
	protected void setCourses(Set<String> courses) {
		this.courses = courses;
	}
}
