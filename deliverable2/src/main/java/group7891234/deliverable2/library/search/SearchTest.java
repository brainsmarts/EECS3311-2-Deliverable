package group7891234.deliverable2.library.search;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import group7891234.deliverable2.library.BookPublisher;
import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.search.AuthorStrategy;
import group7891234.deliverable2.library.search.Search;
import group7891234.deliverable2.library.search.TitleStrategy;
import group7891234.deliverable2.library.search.TypeStrategy;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


class searchTest {

	//private LibraryDataBase ldb = LibraryDataBase.getInstance();
	//private Item tb;
	
	
	//author strategy for publisher names
	@Test
	void authStratTest1() throws Exception {
		// create book to borrow
		LibraryDataBase boo = LibraryDataBase.getInstance();
		Search searching = new Search();
		SearchStrategy auth = new AuthorStrategy();
		searching.setStrategy(auth);
		
		
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

		ItemBuilder bib = new ItemBuilder();
		BookPublisher publisher2 = new BookPublisher("pants", publisherBooks);
		boo.addPublisher(publisher2);
		bib.buildPublisher(publisher2);
		bib.buildId("what");
		bib.buildContent("woof");
		bib.buildName("biscuit");
		bib.buildPrice(123.45);
		bib.buildType(ItemType.TEXTBOOK);
		publisherBooks.add("biscuit");
		
		Item tb = pib.build();
		Item tbb = bib.build(); // built textbook now exists
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);
		
		assertTrue(searching.getSearchResults("pants").contains(tbb));
		assertFalse(searching.getSearchResults("pants").contains(tb));

	}
	
	@Test
	void authStratTest2() throws Exception {
		// create book to borrow
		LibraryDataBase boo = LibraryDataBase.getInstance();
		Search searching = new Search();
		
		
		ItemBuilder pib = new ItemBuilder();
		pib.buildId("accordion");
		pib.buildContent("5-4 swing time in d minor");
		pib.buildName("biscuit town");
		pib.buildPrice(123.45);
		pib.buildType(ItemType.TEXTBOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher1 = new BookPublisher("pants bird", publisherBooks);
		boo.addPublisher(publisher1);
		pib.buildPublisher(publisher1);

		ItemBuilder bib = new ItemBuilder();
		BookPublisher publisher2 = new BookPublisher("pants", publisherBooks);
		boo.addPublisher(publisher2);
		bib.buildPublisher(publisher2);
		bib.buildId("what");
		bib.buildContent("woof");
		bib.buildName("biscuit");
		bib.buildPrice(123.45);
		bib.buildType(ItemType.TEXTBOOK);
		publisherBooks.add("biscuit");
		
		Item tb = pib.build();
		Item tbb = bib.build(); // built textbook now exists
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);
		
		try {
			searching.getSearchResults("pants");
		} catch (Exception e) {
			System.out.println("thrown");
		}
		
		
	}

	@Test
	void titleStratTest1() throws Exception {
		LibraryDataBase boo = LibraryDataBase.getInstance();
		Search searching = new Search();
		SearchStrategy tite = new TitleStrategy();
		searching.setStrategy(tite);
		
		
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

		ItemBuilder bib = new ItemBuilder();
		BookPublisher publisher2 = new BookPublisher("pants", publisherBooks);
		boo.addPublisher(publisher2);
		bib.buildPublisher(publisher2);
		bib.buildId("what");
		bib.buildContent("woof");
		bib.buildName("name");
		bib.buildPrice(123.45);
		bib.buildType(ItemType.TEXTBOOK);
		publisherBooks.add("biscuit");
		
		Item tb = pib.build();
		Item tbb = bib.build(); // built textbook now exists
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);
		
		assertTrue(searching.getSearchResults("name").contains(tbb));
		assertFalse(searching.getSearchResults("name").contains(tb));


	}

	
	@Test
	void typeStratTest1() throws Exception {
		LibraryDataBase boo = LibraryDataBase.getInstance();
		Search searching = new Search();
		SearchStrategy type = new TypeStrategy();
		searching.setStrategy(type);
		
		
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

		ItemBuilder bib = new ItemBuilder();
		BookPublisher publisher2 = new BookPublisher("pants", publisherBooks);
		boo.addPublisher(publisher2);
		bib.buildPublisher(publisher2);
		bib.buildId("what");
		bib.buildContent("woof");
		bib.buildName("biscuit");
		bib.buildPrice(123.45);
		bib.buildType(ItemType.ONLINEBOOK);
		publisherBooks.add("biscuit");
		
		Item tb = pib.build();
		Item tbb = bib.build(); // built textbook now exists
		//this.tb = (TextBook) tb;
		boo.addItem(tb);
		boo.addItem(tbb);
		
		assertTrue(searching.getSearchResults("textbook").contains(tb));
		assertFalse(searching.getSearchResults("textbook").contains(tbb));

	}

	
}
