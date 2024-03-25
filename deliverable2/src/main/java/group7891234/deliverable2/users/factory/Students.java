package group7891234.deliverable2.users.factory;

import java.util.HashSet;
import java.util.Set;

import group7891234.deliverable2.users.User;

public class Students extends User{
	Set<String> courses;
	
	public Students(String username, String password, String email) {
		super(username, password, email);
		this.type = UserType.STUDENT;
		courses = new HashSet<>();
		// TODO Auto-generated constructor stub
	}
	
	public void setCourses(Set<String> courses) {
		this.courses = courses;
	}
	
	@Override
	public Set<String> getCourses() {
		return courses;
	}
}
