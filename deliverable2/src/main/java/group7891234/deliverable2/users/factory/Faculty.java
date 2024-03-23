package group7891234.deliverable2.users.factory;

import java.util.Set;
import group7891234.deliverable2.users.User;

public class Faculty extends User{
	Set<String> courses;
	Set<String> textbookHistory;

	public Faculty(String username, String password, String email) {
		super(username, password, email);
		// TODO Auto-generated constructor stub
	}
	
	public void setCourses(Set<String> courses) {
		this.courses = courses;
	}
	
	protected void setTextBookHistory(Set<String> history) {
		this.textbookHistory = history;
	}
	
	public void addFacultyHistory(String id) {
		//ask library database to also rewrite
		textbookHistory.add(id);
	}
}
