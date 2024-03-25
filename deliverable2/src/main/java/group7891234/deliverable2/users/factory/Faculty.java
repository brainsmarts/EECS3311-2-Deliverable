package group7891234.deliverable2.users.factory;

import java.util.HashSet;
import java.util.Set;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;

public class Faculty extends User{
	Set<String> courses;
	Set<String> textbookHistory;

	public Faculty(String username, String password, String email) {
		super(username, password, email);
		type = UserType.FACULTY;
		courses = new HashSet<>();
		textbookHistory = new HashSet<>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void borrow(String item) {
		super.borrow(item);
		try {
			if(LibraryDataBase.getInstance().getItem(item).getType() == ItemType.TEXTBOOK) {
				addFacultyHistory(item);
				LibraryDataBase.getInstance().addFacultyToTextbookEditions(item, getUserName());
				UserDataBase.getInstance().updateUsersFile();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Set<String> getCourses(){
		return courses;
	}
	
	public void setCourses(Set<String> courses) {
		this.courses = courses;
	}
	
	public void setTextBookHistory(Set<String> history) {
		this.textbookHistory = history;
	}
	
	public void addFacultyHistory(String id) {
		//ask library database to also rewrite
		System.out.println("Added Book " + id);
		textbookHistory.add(id);
	}
	
	@Override
	public Set<String> getTBhistory(){
		return this.textbookHistory;
	}
}
