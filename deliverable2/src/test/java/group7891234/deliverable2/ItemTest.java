package group7891234.deliverable2.library.item;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import group7891234.deliverable2.library.BookPublisher;
import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.search.Search;
import group7891234.deliverable2.library.search.SearchStrategy;
import group7891234.deliverable2.library.search.TypeStrategy;
import group7891234.deliverable2.library.item.Item;

class ItemTest {

	//public LibraryBuilder buildType(ItemType type);
	//public LibraryBuilder buildId(String id);
	//public LibraryBuilder buildName(String name) ;
	//public LibraryBuilder buildPublisher(BookPublisher publisher);
	//public LibraryBuilder buildContent(String content);
	//public LibraryBuilder buildPrice(double price);
	//public Item build();
	
	@Test
	void textbookTest1() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
			
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.TEXTBOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		Item tb = pib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);

		
		assertTrue(tb.getType().equals(ItemType.TEXTBOOK));
	}
	
	@Test
	void onlineBookTest1() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
			
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.ONLINEBOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		Item tb = pib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);

		
		assertTrue(tb.getType().equals(ItemType.ONLINEBOOK));
	}

	@Test
	void newsletterTest1() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
			
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.NEWSLETTER);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		
		
		Item tb = pib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);

		
		assertTrue(tb.getType().equals(ItemType.NEWSLETTER));
	}

	@Test
	void bookTest1() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
			
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.BOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		Item tb = pib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);

		
		assertTrue(tb.getType().equals(ItemType.BOOK));
	}

	@Test
	void equalsTest1() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
			
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.BOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		ItemBuilder bib = new ItemBuilder();
		bib.buildId("accordion");
		bib.buildContent("5-4 swing time in d minor");
		bib.buildName("biscuit town");
		bib.buildPrice(123.45);
		bib.buildType(ItemType.BOOK);
		
		bib.buildPublisher(publisher1);
		
		Item tb = pib.build();
		Item tbb = bib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);

		
		assertTrue(tb.equals(tbb));
	}
	
	@Test
	void equalsTest2() {
		LibraryDataBase boo = LibraryDataBase.getInstance();
		
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.BOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("chance bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);
		
		ItemBuilder bib = new ItemBuilder();
		bib.buildId("bum");
		bib.buildContent("3");
		bib.buildName("bis town");
		bib.buildPrice(23.45);
		bib.buildType(ItemType.BOOK);
		
		publisherBooks.add("bistown");
		BookPublisher publisher2 = new BookPublisher("chance", publisherBooks);
		boo.addPublisher(publisher2);
		bib.buildPublisher(publisher2);
		
		Item tb = pib.build();
		Item tbb = bib.build();
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);

		
		assertFalse(tb.equals(tbb));
	}

}
