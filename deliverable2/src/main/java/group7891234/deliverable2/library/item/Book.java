package group7891234.deliverable2.library.item;

import java.time.LocalDate;

import group7891234.deliverable2.library.BookPublisher;
import group7891234.deliverable2.users.User;

public class Book extends Item{
	LocalDate dueDate;
	User borrowedBy;

	protected Book(ItemType type,  String id, String name, BookPublisher publisher, String content) {
		super(type, id, name, publisher, content);
		// TODO Auto-generated constructor stub
	}
	
	public void borrow(User user) {
		
	}
	
	public void returnBook() {
		
	}
	
	public void isLost() {
		
	}
	
	public boolean isOverdue() {
		LocalDate today = LocalDate.now();
		return today.isAfter(dueDate);
	}

	@Override
	public void accepts(ItemVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitBook(this);
	}
	
	
}
