package group7891234.deliverable2.library.TextBookEditions;

import java.util.HashSet;
import java.util.Set;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.Faculty;

public class TextBookEdition {
	String series;
	Set<TextBook> editions;
	Set<TextBookNotification> facultyNotifications;
	Set<String> faculty;
	
	public TextBookEdition(String series) {
		this.series = series;
		editions = new HashSet<>(); 
		facultyNotifications = new HashSet<>();
		faculty = new HashSet<>();
	}

	// if a textbook is added then yuh
	public Set<String> getFaculty() {
		return faculty;
		
	}
	
	public boolean isPartOfSeries(TextBook book) {
		System.out.println(book.getId().split("#")[0]);
		System.out.println(series);
		if (book.getId().split("#")[0].compareTo(series) == 0) {
			try {
				editions.add(book);
				notifyFaculty();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	public void notifyFaculty() {
		facultyNotifications.forEach(notification -> notification.update());
	}
	
	public String getSeries() {
		return series;
	}

	public Set<TextBook> getEditions() {
		return editions;
	}

	public void setEditions(Set<TextBook> books) {
		this.editions = books;
	}

	public void setFacultyNotifications(Set<String> facultyList) {
		faculty = facultyList;
		for(String username: facultyList) {
			EmailNotification notification = new EmailNotification(UserDataBase.getInstance().getUser(username));
			facultyNotifications.add(notification);
		}
	}
}
