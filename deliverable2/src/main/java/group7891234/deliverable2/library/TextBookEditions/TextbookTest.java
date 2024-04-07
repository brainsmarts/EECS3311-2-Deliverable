package group7891234.deliverable2.library.TextBookEditions; 
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.*;
import org.junit.jupiter.api.Assertions;

import group7891234.deliverable2.library.*;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;
public class TextbookTest {
	
	
	/*@Test
	public void EmailNotiftest1() {
		UserDataBase database = UserDataBase.getInstance();
		User test = database.getUser("TaylorSwift");
	
		EmailNotification EmailNotif = new EmailNotification(test);
	
		assertEquals(EmailNotif, EmailNotif);
		EmailNotif.update();
		System.out.println("");
	}
	@Test
	public void EmailNotiftest2() {
		EmailNotification EmailNotif = new EmailNotification(null);
		
		assertEquals(EmailNotif, EmailNotif);
		//Assertions.assertThrows(EmailNotif.update(), NullPointerException);
	}
	@Test
	public void EmailNotiftest3() {
		UserDataBase database = UserDataBase.getInstance();
		User test3 = database.getUser("HomerTime");
		
		
		EmailNotification EmailNotif2 = new EmailNotification(test3);
		EmailNotification EmailNotif3 = new EmailNotification(test3);
		assertNotEquals(EmailNotif2, EmailNotif3);
		EmailNotif2.update();
	}
	@Test
	public void EmailNotiftest4() {
		UserDataBase database = UserDataBase.getInstance();
		User test4 = database.getUser("braindon");		
		EmailNotification EmailNotif = new EmailNotification(test4);
		String msg = EmailNotif.toString();
		assertEquals(msg , EmailNotif.toString());
		EmailNotif.update();
	}
	@Test
	public void EmailNotiftest5() {
		UserDataBase database = UserDataBase.getInstance();
		User test5 = database.getUser("brunni");		
		EmailNotification EmailNotif = new EmailNotification(test5);
		assertNotEquals(test5.toString(), EmailNotif.toString());
		EmailNotif.update();
	}
	
	@Test
	public void TextbookEditionTest1() {
		TextBookEdition tb = new TextBookEdition("Womp", "womp");
		assertEquals(tb.getSeries(), "Womp");
		assertEquals(tb.getOnlineBook() ,"womp");
	}*/
	@Test
	public void TextbookEditionTest2() throws Exception {
		
		LibraryDataBase database = LibraryDataBase.getInstance();
		TextBook book1 = (TextBook) database.getItem("cat#1");
		TextBook book2 = (TextBook) database.getItem("cat#2");
		TextBook book3 = (TextBook) database.getItem("cat#3");
		TextBookEdition tb = new TextBookEdition("cat", "The Feline Chronicles: A History of Cats");
		tb.addBook(book1);
		tb.addBook(book2);
		tb.addBook(book3);
		assertTrue(tb.isPartOfSeries(book1));
		assertTrue(tb.isPartOfSeries(book2));
		assertTrue(tb.isPartOfSeries(book3));
		
		
		
	}

}

/*assertNull(null);
assertTrue(true);
assertFalse(false);
assertEquals(0, 0);*/
