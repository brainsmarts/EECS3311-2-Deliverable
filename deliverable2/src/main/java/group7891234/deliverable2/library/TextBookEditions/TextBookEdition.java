package group7891234.deliverable2.library.TextBookEditions;

import java.util.Set;

import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.users.factory.Faculty;

public class TextBookEdition{
	
	String textbookName;
	Set<TextBook> editions;
	Set<TextBookNotification> facultyNotifications;
	
	//if a textbook is added then yuh
	public void addTextBook(TextBook book) {
		editions.add(book);
		notifyFaculty();
	}
	
	public void notifyFaculty() {
		facultyNotifications.forEach(notification -> notification.update());
	}
}
