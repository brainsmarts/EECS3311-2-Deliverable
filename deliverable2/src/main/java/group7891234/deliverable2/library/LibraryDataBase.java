package group7891234.deliverable2.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import group7891234.deliverable2.library.TextBookEditions.TextBookEdition;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.library.search.AuthorStrategy;
import group7891234.deliverable2.library.search.Search;
import group7891234.deliverable2.library.search.TitleStrategy;
import group7891234.deliverable2.library.search.TypeStrategy;
import group7891234.deliverable2.users.User;

public class LibraryDataBase {
	private String 
			item_path = "src/main/resources/library_items.csv", 
			publisher_path = "src/main/resources/library_publishers.csv", 
			edition_path = "src/main/resources/library_editions.csv",
			borrowing_path = "src/main/resources/library_borrowing.csv";
	//type, id, name, enabled, publisher, content
	private Set<Item> items;
	//name, books
	private Set<BookPublisher> publishers;
	//name, books, faculty to notice
	List<TextBookEdition> textbook_series;
	private Map<String, Set<String>> borrowMap;
	private final int numOfPhysicalItems = 20;
	
	private static LibraryDataBase instance; 
	
	private LibraryDataBase() {
		//populate items, publishers, and textbook series with the exel/cvs
		
		items = new HashSet<Item>();
		publishers = new HashSet<BookPublisher>();
		textbook_series = new ArrayList<TextBookEdition>();
		borrowMap = new HashMap<>(); 
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

	public Item borrow(User user, String itemName) {
		Item item = null;
		try{
			//check if book exists in borrow map
			if(borrowMap.containsKey(itemName)) {
				//check if theres enough to go around
				if(borrowMap.get(itemName).size() < numOfPhysicalItems) {
					borrowMap.get(itemName).add(user.getUserName());
					return getItem(itemName);
				} else {
					throw new IllegalArgumentException("No More to rent/borrow");
				}
				//if so then return book and add user to map otherwise throw error ig :|
			}
			borrowMap.computeIfAbsent(itemName, k -> new HashSet<>()).add(user.getUserName());
			return getItem(itemName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		throw new NullPointerException(itemName + " could not be found");
	}
	//provides a new set of items but not the actual items of the library base to prevent manipulation from outside of the library class.
	public Set<Item> getItems(){
		HashSet<Item> clone = new HashSet<Item>();
		clone.addAll(items);
		return clone;
	}
	
	public Item getItem(String id) throws Exception {
        for (Item item : items) {
            if(item.getId().compareTo(id) == 0) {
            	return item;
            }
        }

		throw new IllegalArgumentException("Item with ID " + id + " not found");
	}
	
	public BookPublisher getPublisher (String name) throws Exception {
		for(BookPublisher publisher: publishers) {
			if(publisher.getName().compareToIgnoreCase(name) == 0) {	
				return publisher;
			}
		}
		
		throw new IllegalArgumentException("Publisher could not be found");
	}
	
	public Set<Item> search(String search) {
		Search searchMethod = new Search();
		searchMethod.setStrategy(new TitleStrategy());
		String command = search.substring(0, 2);
		if(command.compareTo("/p") == 0) {
			searchMethod.setStrategy(new AuthorStrategy());
		}else if(command.compareTo("/t") == 0) {
			searchMethod.setStrategy(new TypeStrategy());
		}
		return searchMethod.getSearchResults(search.substring(3));
	}
	
	//do not use during initilization ONLY USE after
	public void addItem(Item item) {
		items.add(item);
		
		if(item.getType() == ItemType.TEXTBOOK) {
			boolean editionExists = false;
			for(TextBookEdition edition: this.textbook_series) {
				editionExists = edition.isPartOfSeries((TextBook)item);
				break;
			}
			
			if(!editionExists) {
				TextBookEdition new_series = new TextBookEdition(item.getId().split("#")[0],"");
				new_series.isPartOfSeries((TextBook)item);
				this.textbook_series.add(new_series);
			}
			updateTextBookEditionsFile();
		}
		updateItemsFile();
	}
	
	//do not use during initilization ONLY USE after
	public void addPublisher(BookPublisher publisher) {
		publishers.add(publisher);
		updatePublisherFile();
	}

	public void updateFile() {
		updatePublisherFile();
		updateTextBookEditionsFile();
		updateItemsFile();
	}
	public void updatePublisherFile() {
		//get publisher list and rewrites a specific Publisher
		//first read all
		String[] header = {"name", "books"};
		String[] line = new String[2]; 
 		try {
			CsvWriter writer = new CsvWriter(this.publisher_path);
			//publisher is literally name then books, just rewrite the entire thing
			writer.writeRecord(header);
			for(BookPublisher publisher: publishers) {
				line[0] = publisher.getName();
				line[1] = String.join(" ", publisher.getBooks());
				writer.writeRecord(line);
			}
			writer.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateBorrowingFile() {
		//get publisher list and rewrites a specific Publisher
		//first read all
		String[] header = {"bookid", "users"};
		String[] line = new String[2]; 
 		try {
			CsvWriter writer = new CsvWriter(this.publisher_path);
			//publisher is literally name then books, just rewrite the entire thing
			writer.writeRecord(header);
			for (Map.Entry<String, Set<String>> entry : borrowMap.entrySet()) {
				line[0] = entry.getKey();
				line[1] = String.join(" ", entry.getValue());
				writer.writeRecord(line);
			}
			writer.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateTextBookEditionsFile() {
		String[] header = {"series","online", "books","faculty"};
		String[] line = new String[4]; 
		
 		try {
 			
			CsvWriter writer = new CsvWriter(this.edition_path);
			//publisher is literally name then books, just rewrite the entire thing
			writer.writeRecord(header);
			for(TextBookEdition series : this.textbook_series) {
				String tempBooks = "";
				String tempFaculty = "";
				System.out.println(series.getSeries() + " Updating file info");
				line[0] = series.getSeries();
				line[1] = series.getOnlineBook();
				for(TextBook book:series.getEditions())
					tempBooks += book.getId() + " ";
				line[2] = tempBooks;
				line[3] = String.join(" ",series.getFaculty());
				writer.writeRecord(line);
			}
			writer.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void populate() throws IOException {
		//first populate publishers
		CsvReader reader = new CsvReader(publisher_path); // Open the reader
		  try {
		    reader.readHeaders();

		    while (reader.readRecord()) {
		      BookPublisher publisher = createPublisherFromRecord(reader);
		      publishers.add(publisher);
		    }
		    reader.close();
		    
		    
		    reader = new CsvReader(item_path);
		    reader.readHeaders();
		    
		    while (reader.readRecord()) {
			      Item item = createItemFromRecord(reader);
			      items.add(item);
			}
		    reader.close();
		    
		    reader = new CsvReader(edition_path);
		    reader.readHeaders();
		    
		    while(reader.readRecord()) {
		    	TextBookEdition series = createTextBookEditionFromRecord(reader);
		    	textbook_series.add(series);
		    }
		    
		    reader = new CsvReader(borrowing_path);
		    reader.readHeaders();
		    	
		    createBorrowingMap(reader);
		  }catch(Exception e) {
			  e.printStackTrace();
		  }finally {
			  //update each file to ensure they are up to date
			updateFile();
		    reader.close();  // Ensure closing the reader in a finally block
		  }
		//then populate items
		//then editions
	}

	public void updateItemsFile() {
		String[] header = {"type", "id","name","price","enabled","publisher","content"};
		String[] line = new String[7];
		try {
			CsvWriter writer = new CsvWriter(this.item_path);
			//type,id,name,price,enabled,publisher,content
			writer.writeRecord(header);
			for(Item item : items) {
				line[0] = item.getType().toString();
				line[1] = item.getId();
				line[2] = item.getName();
				line[3] = Double.toString(item.getPrice());
				line[4] = Boolean.toString(item.isEnabled());
				line[5] = item.getPublisher().getName();
				line[6] = item.getContent();
				writer.writeRecord(line);
			}
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createBorrowingMap(CsvReader reader) throws IOException {
		while(reader.readRecord()) {
			reader.get("bookid");
			borrowMap.put(reader.get(reader.get("bookid")), new HashSet<>(Arrays.asList(reader.get("users").split(" "))));
	    }
	}

	private TextBookEdition createTextBookEditionFromRecord(CsvReader reader) {
		TextBookEdition series = null;
		try {
			series = new TextBookEdition(reader.get("series"), reader.get("online"));
			String[] test = reader.get("books").split(" ");
			Set<TextBook> list = new HashSet<>();
			for(String string : test) {
				try {
					list.add((TextBook) getItem(string));
				}catch(Exception e){
					
				}
				
			}
			series.setEditions(list);
			series.setFacultyNotifications(new HashSet<String>(Arrays.asList(reader.get("faculty").split(" "))));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return series;
	}

	private Item createItemFromRecord(CsvReader reader) {
		Item item = null;
		try {
			ItemType type = ItemType.valueOf(reader.get("type"));
			String id = reader.get("id");
			String name = reader.get("name");
			double price =Double.parseDouble(reader.get("price"));
			BookPublisher publisher;
			try {
				publisher = getPublisher(reader.get("publisher"));
			}catch(Exception e) {
				publisher = new BookPublisher(reader.get("publisher"), new HashSet<>());
				publishers.add(publisher);
			}
			publisher.addBook(id);
			
			String content = reader.get("content");
			item = new ItemBuilder().buildId(id).buildName(name).buildPrice(price).buildPublisher(publisher).buildContent(content).buildType(type).build();
			if(Boolean.parseBoolean(reader.get("enabled"))) {
				item.enable();
			}else {
				item.disable();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(item == null)
			throw new IllegalArgumentException();
		return item;
	}

	private BookPublisher createPublisherFromRecord(CsvReader reader) {
		// TODO Auto-generated method stub
		BookPublisher publisher = null;
		try {
			publisher = new BookPublisher(reader.get("name"), new HashSet<>(Arrays.asList(reader.get("books").split(" "))));	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(publisher == null)
			throw new IllegalArgumentException();
		return publisher;
	}

	public Set<BookPublisher> getPublishers() {
		return publishers;
	}

	public void addFacultyToTextbookEditions(String item, String user) {
		//find textbook edition
		for(TextBookEdition edition: this.textbook_series) {
			if(edition.getSeries().compareTo(item.split("#")[0]) == 0) {
				edition.addFaculty(user);
				break;
			}
		}
		updateTextBookEditionsFile();
	}

	public Item getSeriesOnlineBook(String series) {
		for(TextBookEdition editions : this.textbook_series) {
			if(editions.getSeries().compareTo(series) == 0) {
				try {
					return getItem(editions.getOnlineBook());
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		}
		throw new IllegalArgumentException("OnlineBook Not Found");
	}
}
