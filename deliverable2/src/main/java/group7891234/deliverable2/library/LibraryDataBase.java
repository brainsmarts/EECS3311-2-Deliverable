package group7891234.deliverable2.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.csvreader.CsvReader;

import group7891234.deliverable2.library.TextBookEditions.TextBookEdition;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.search.AuthorStrategy;
import group7891234.deliverable2.library.search.Search;
import group7891234.deliverable2.library.search.TitleStrategy;
import group7891234.deliverable2.library.search.TypeStrategy;

public class LibraryDataBase {
	private String 
			item_path = "src/main/resources/library_items.csv", 
			publisher_path = "src/main/resources/library_publishers.csv", 
			edition_path = "src/main/resources/library_editions.csv";
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
		try {
			populate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	private void populate() throws IOException {
		//first populate publishers
		CsvReader reader = new CsvReader(publisher_path); // Open the reader
		  try {
		    reader.readHeaders();

		    while (reader.readRecord()) {
		      Publisher publisher = createPublisherFromRecord(reader);
		      publishers.add(publisher);
		    }
		    
		    reader = new CsvReader(item_path);
		    reader.readHeaders();
		    
		    while (reader.readRecord()) {
			      Item item = createItemFromRecord(reader);
			      items.add(item);
			}
		    
		    reader = new CsvReader(edition_path);
		    reader.readHeaders();
		  } finally {
		    reader.close();  // Ensure closing the reader in a finally block
		  }
		//then populate items
		//then editions
	}
	
	private Item createItemFromRecord(CsvReader reader) {
		Item item = null;
		try {
			System.out.println(reader.get("type"));
			ItemType type = ItemType.valueOf(reader.get("type"));
			String id = reader.get("id");
			String name = reader.get("name");
			double price =Double.parseDouble(reader.get("price"));
			Publisher publisher = getPublisher(reader.get("publisher"));
			String content = reader.get("content");
			item = new ItemBuilder().buildId(id).buildName(name).buildPrice(price).buildPublisher(publisher).buildContent(content).buildType(type).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(item == null)
			throw new IllegalArgumentException();
		return item;
	}

	private Publisher createPublisherFromRecord(CsvReader reader) {
		// TODO Auto-generated method stub
		Publisher publisher = null;
		try {
			publisher = new Publisher(reader.get("name"), new HashSet<>(Arrays.asList(reader.get("books").split(" "))));	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(publisher == null)
			throw new IllegalArgumentException();
		return publisher;
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
		Search searchMethod = new Search();
		searchMethod.setStrategy(new TitleStrategy());
		String command = search.substring(0, 1);
		if(command.compareTo("/p") == 0) {
			searchMethod.setStrategy(new AuthorStrategy());
		}else if(command.compareTo("/t") == 0) {
			searchMethod.setStrategy(new TypeStrategy());
		}
		return searchMethod.getSearchResults(search.substring(3));
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
