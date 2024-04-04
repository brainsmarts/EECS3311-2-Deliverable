 package group7891234.deliverable2.library;

import java.util.Set;

import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;

public class Publisher {
	private Set<String> books_published;
	private String name;
	
	public Publisher(String name, Set<String> books_published){
		this.name = name;
		this.books_published = books_published;
	}
	
	public void populate() {
		for(Item item: LibraryDataBase.getInstance().getItems()) {
			if(item.getPublisher().getName().compareTo(name) == 0) {
				books_published.add(name);
			}
		}
	}
	
	public void addBook(String bookID) {
		books_published.add(bookID);
	}
	
	public String getName() {
		return name;
	}
	
	public Set<String> getBooks() {
		return books_published;
	}
	
	public void grantPermission(boolean grant) {
	}
	
	public void grantDiscount(double percent) {
		for(String name : books_published) {
			try {
				Item item = LibraryDataBase.getInstance().getItem(name);
				double newPrice = percent * item.getPrice();
				item.setPrice(newPrice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return (publisher.getName().compareTo(name) == 0);
		
	}
} // praise be to allah 
