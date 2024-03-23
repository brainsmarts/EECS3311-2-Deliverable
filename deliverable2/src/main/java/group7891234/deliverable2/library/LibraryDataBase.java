package group7891234.deliverable2.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import group7891234.deliverable2.library.TextBookEditions.TextBookEdition;
import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;

public class LibraryDataBase {
	//type, id, name, enabled, publisher, content
	private Set<Item> items;
	//name, books
	private Set<Publisher> publishers;
	//name, books, faculty to notice
	List<TextBookEdition> textbook_series;
	
	private static LibraryDataBase instance; 
	
	private LibraryDataBase() {
		//populate items, publishers, and textbook series with the exel/cvs
		
		items = new HashSet<Item>();
		publishers = new HashSet<Publisher>();
		textbook_series = new ArrayList<TextBookEdition>();
		populate();
	}
	
	public static LibraryDataBase getInstance() {
		if(instance == null) {
			synchronized(LibraryDataBase.class) {
				if(instance == null) {
					instance = new LibraryDataBase();
				}
			}
		}
		return instance;
	}
	
	private void populate() {
		
	}
	
	//provides a new set of items but not the actual items of the library base to prevent manipulation from outside of the library class.
	public Set<Item> getItems(){
		HashSet<Item> clone = new HashSet<Item>();
		clone.addAll(items);
		return clone;
	}
	
	public Item getItem(String id) throws Exception {
		for(Item item: items) {
			if(item.getId().compareTo(id) == 0) {
				return item;
			}
		}
		
		throw new IllegalArgumentException("Item with ID " + id + " not found");
	}
	
	public Publisher getPublisher(String name) {
		for(Publisher publisher: publishers) {
			if(publisher.getName().compareToIgnoreCase(name) == 0) {
				return publisher;
			}
		}
		
		throw new IllegalArgumentException("Publisher with name " + name + "was not found");
	}
	
	public Set<Item> search(String search) {
		return null;
	}
	
	public void addItem(Item item) {
		items.add(item);
		//write it into cvs
	}
	
	public void addPublisher(Publisher publisher) {
		publishers.add(publisher);
		//write it into cvs
	}
	
	public void addTextbookEdition(TextBookEdition tbe) {
		textbook_series.add(tbe);
		//write it into cvs
	}
}
