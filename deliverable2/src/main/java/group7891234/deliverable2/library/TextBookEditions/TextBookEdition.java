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
	
	public TextBookEdition(String series) {
		editions = new HashSet<>(); 
		facultyNotifications = new HashSet<>();
	}

	// if a textbook is added then yuh
	public void addTextBook(TextBook book) {
		editions.add(book);
		notifyFaculty();
	}
	
	public boolean isPartOfSeries(String id) {
		if (id.split("#")[0].compareTo(series) == 0) {
			try {
				if(LibraryDataBase.getInstance().getItem(id).getType() == ItemType.TEXTBOOK) {
					editions.add((TextBook) LibraryDataBase.getInstance().getItem(id));
					notifyFaculty();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
		for(String username: facultyList) {
			EmailNotification notification = new EmailNotification(UserDataBase.getInstance().getUser(username));
			facultyNotifications.add(notification);
		}
	}
}
