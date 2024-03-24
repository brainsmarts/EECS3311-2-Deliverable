 package group7891234.deliverable2.library;

import java.util.Set;

import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;

public class Publisher {
	private Set<String> books_published;
	private String name;
	
	Publisher(String name, Set<String> books_published){
		this.name = name;
		this.books_published = books_published;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<String> getBooks() {
		return books_published;
	}
	
	public void grantPermission(boolean grant) {
		//what
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return (publisher.getName().compareTo(name) == 0);
		
	}
}
