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

public class LibraryDataBase {
	private String 
			item_path = "src/main/resources/library_items.csv", 
			publisher_path = "src/main/resources/library_publishers.csv", 
			edition_path = "src/main/resources/library_editions.csv";
	//type, id, name, enabled, publisher, content
	private Set<Item> items;
	private Map<String, Item> itemLookUp;
	//name, books
	private Set<Publisher> publishers;
	//name, books, faculty to notice
	List<TextBookEdition> textbook_series;
	private Map<Item, Set<String>> borrowMap;
	
	private static LibraryDataBase instance; 
	
	private LibraryDataBase() {
		//populate items, publishers, and textbook series with the exel/cvs
		
		items = new HashSet<Item>();
		publishers = new HashSet<Publisher>();
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

	//provides a new set of items but not the actual items of the library base to prevent manipulation from outside of the library class.
	public Set<Item> getItems(){
		HashSet<Item> clone = new HashSet<Item>();
		clone.addAll(items);
		return clone;
	}
	
	public Item getItem(String id) throws Exception {
		if(itemLookUp == null) {
			itemLookUp = new HashMap<>();
	        for (Item item : items) {
	            itemLookUp.put(item.getId(), item);
	        }
		}
		Item item = itemLookUp.get(id);
	    if (item != null) 
	        return item;
		
		throw new IllegalArgumentException("Item with ID " + id + " not found");
	}
	
	public Publisher getPublisher(String name) {
		for(Publisher publisher: getPublishers()) {
			if(publisher.getName().compareToIgnoreCase(name) == 0) {
				return publisher;
			}
		}
		
		return new Publisher("Unknown Publisher", Collections.EMPTY_SET);
	}
	
	public Set<Item> search(String search) {
		Search searchMethod = new Search();
		searchMethod.setStrategy(new TitleStrategy());
		String command = search.substring(0, 2);
		if(command.compareTo("/p") == 0) {
			System.out.println("Author Strategy");
			searchMethod.setStrategy(new AuthorStrategy());
		}else if(command.compareTo("/t") == 0) {
			searchMethod.setStrategy(new TypeStrategy());
		}
		return searchMethod.getSearchResults(search.substring(3));
	}
	
	public void addTextbook(TextBook tb) {
		for(TextBookEdition series: textbook_series) {
			if(tb.getId().split("#")[0].compareTo(series.getSeries()) == 0){
				series.addTextBook(tb);
				return;
			}
		}
		TextBookEdition newSeries = new TextBookEdition(tb.getId().split("#")[0]);
		newSeries.addTextBook(tb);
		textbook_series.add(newSeries);
	}
	
	public void addItem(Item item) {
		if(items.contains(item)) {
			System.out.print("Already In DataBase");
			return;
		}
		items.add(item);
		List<String[]> lines = new ArrayList<>();
		try {
			CsvReader reader = new CsvReader(this.item_path);

			while(reader.readRecord()) {
				  lines.add(reader.getValues());
			}
			
			reader.close();
			
			CsvWriter writer = new CsvWriter(this.item_path);
			
			for(String[] string: lines) {
				writer.writeRecord(string);
			}
			//type id name price enabled publisher content
			writer.write(item.getType().toString());
			writer.write(item.getId());
			writer.write(item.getName());
			writer.write(Double.toString(item.getPrice()));
			writer.write(Boolean.toString(item.isEnabled()));
			writer.write(item.getPublisher().getName());
			writer.write(item.getContent());
			
			writer.close();
		}catch(Exception e) {
			
		}
		
		if(item.getType() == ItemType.TEXTBOOK)
			updateTextBookEditionsFile(item.getId().split("#")[0]);
	}
	
	public void addPublisher(Publisher publisher) {
		if(getPublishers().contains(publisher))
			return;
		getPublishers().add(publisher);
		List<String[]> lines = new ArrayList<>();
		try {
			CsvReader reader = new CsvReader(this.publisher_path);

			while(reader.readRecord()) {
				  lines.add(reader.getValues());
			}
			
			reader.close();
			
			CsvWriter writer = new CsvWriter(this.publisher_path);
			
			for(String[] string: lines) {
				writer.writeRecord(string);
			}
			
			writer.write(publisher.getName());
			writer.write("");
			
			writer.close();
		}catch(Exception e) {
			
		}
	}
	
	public Publisher createPublisher(String name) {
		Publisher publisher = new Publisher(name, Collections.emptySet());
		addPublisher(publisher);
		return publisher;
	}

	public void updatePublisherFile(String publisherName) {
		//get publisher list and rewrites a specific Publisher
		//first read all
		List<String[]> lines = new ArrayList<>();
		try {
			CsvReader reader = new CsvReader(this.publisher_path);
			String booklist = "";
			
			while(reader.readRecord()) {
				  lines.add(reader.getValues());
			}
			
			reader.close();
			
			for(String[] string: lines) {
				if(string[0].compareTo(publisherName) == 0) {
					for(Item item: items) {
						if(item.getPublisher().getName().compareTo(publisherName) == 0) {
							booklist += item.getId() + " ";
						}
					}
					string[1] = booklist;
					break;
				}
			}
			
			CsvWriter writer = new CsvWriter(this.publisher_path);
			for(String[] string: lines) {
				writer.writeRecord(string);
			}
			writer.close();
			
		}catch(Exception e) {
			
		}
		
	}

	public void updateTextBookEditionsFile(String series) {
		List<String[]> lines = new ArrayList<>();
		try {
			CsvReader reader = new CsvReader(this.edition_path);
			String booklist = "";
			
			while(reader.readRecord()) {
				  lines.add(reader.getValues());
			}
			
			reader.close();
			
			for (int i = 0; i < lines.size(); i++) {
			    String[] line = lines.get(i);
			    if (line[0].compareTo(series) == 0) {
			        for (Item item : items) {
			            if (item.getId().split("#")[0].compareTo(series) == 0) {
			                booklist += item.getId() + " ";
			            }
			        }
			        line[1] = booklist;
			        lines.set(i, line);
			        break;
			    }
			}
			CsvWriter writer = new CsvWriter(this.edition_path);
			for(String[] string: lines) {
				writer.writeRecord(string);
			}
			
			writer.close();
			
		}catch(Exception e) {
			
		}
	}

	private void populate() throws IOException {
		//first populate publishers
		CsvReader reader = new CsvReader(publisher_path); // Open the reader
		  try {
		    reader.readHeaders();

		    while (reader.readRecord()) {
		      Publisher publisher = createPublisherFromRecord(reader);
		      getPublishers().add(publisher);
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
		    
		  } finally {
			for(Publisher publisher: getPublishers()) {
				//updatePublisherFile(publisher.getName());
			}
		    reader.close();  // Ensure closing the reader in a finally block
		  }
		//then populate items
		//then editions
	}

	private TextBookEdition createTextBookEditionFromRecord(CsvReader reader) {
		TextBookEdition series = null;
		try {
			series = new TextBookEdition(reader.get("series"));
			String[] test = reader.get("books").split(" ");
			Set<TextBook> list = new HashSet<>();
			for(String string : test) {
				list.add((TextBook) getItem(string));
			}
			series.setEditions(list);
			series.setFacultyNotifications(new HashSet<String>(Arrays.asList(reader.get("faculty").split(" "))));
		}catch(Exception e) {
			
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
			Publisher publisher = getPublisher(reader.get("publisher"));
			if(publisher.getName().compareTo("Unknown Publisher") == 0) {
				publisher = new Publisher(reader.get("publisher"), Collections.emptySet());
				addPublisher(publisher);
			}
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

	public Set<Publisher> getPublishers() {
		return publishers;
	}
}
