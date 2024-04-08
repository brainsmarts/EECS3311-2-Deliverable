package group7891234.deliverable2;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import group7891234.deliverable2.library.BookPublisher;
import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.NewsLetter;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.request.RequestType;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.*;

class UserTestCases {
	
	private UserDataBase udb = UserDataBase.getInstance();
	private LibraryDataBase ldb = LibraryDataBase.getInstance();
	//private Item tb;
	private RequestDataBase rdb = RequestDataBase.getinstance();
	
	// student class testing: done
	@Test
	public void studentTest() {
		try {
			udb.registerNewUser("student", "NyOmMMMMy:3", "studn@email.com", UserType.STUDENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Students student = (Students) udb.getUser("student");
		Set<String> courses = new HashSet<>();
		courses.add("crying");
		courses.add("giggle");
		student.setCourses(courses);
		
		Set<String> checkCourses = student.getCourses();
		assertTrue(checkCourses.contains("crying"));
		assertTrue(checkCourses.contains("giggle"));
	}
	
	@Test 
	public void studentTest2() {
		try {
			udb.registerNewUser("student2", "NyOmMMMMy:3", "studn@email.com", UserType.STUDENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User student2 = udb.getUser("student2");
		
		assertEquals("studn@email.com", student2.getEmail());
		assertEquals(UserType.STUDENT, student2.getType());
		assertEquals("NyOmMMMMy:3", student2.getPassword());
	}
	
	// non-faculty class testing: done
	@Test
	public void nonFacultyTest() {
		try {
			udb.registerNewUser("nonfaculty", "NyOmMMMMy:3", "nofasc@email.com", UserType.NON_FACULTY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User nFaculty = udb.getUser("nonfaculty");
		
		assertEquals("nofasc@email.com", nFaculty.getEmail());
		assertEquals(UserType.NON_FACULTY, nFaculty.getType());
		assertEquals("NyOmMMMMy:3", nFaculty.getPassword());
	}
	
	
	// faculty class testing
	@Test
	public void facultyTest() {
		try {
			udb.registerNewUser("faculty1", "NyOmMMMMy:3", "facult@woowie.com", UserType.FACULTY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Faculty f1 = (Faculty) udb.getUser("faculty1");
		
		// adding course 
		Set<String> courses = new HashSet<>();
		courses.add("crying");
		courses.add("giggle");
		f1.setCourses(courses);
		// checking courses
		Set<String> checkCourses = f1.getCourses();
		assertTrue(checkCourses.contains("crying"));
		assertTrue(checkCourses.contains("giggle"));
		
		// setting textbooks
		Set<String> tbhistory = new HashSet<>();
		tbhistory.add("no");
		tbhistory.add("nyo");
		f1.setTextBookHistory(tbhistory);
		// chedcking textbooks
		Set<String> tbHistory = f1.getTBhistory();
		assertTrue(tbHistory.contains("no"));
		assertTrue(tbHistory.contains("nyo"));
		
		// add faculty history
		f1.addFacultyHistory("aaaaaaa");
	}
	
	@Test 
	void facultyTest2() {
		try {
			udb.registerNewUser("faculty2", "NyOmMMMMy:3", "facult@woowie.com", UserType.FACULTY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User f2 = udb.getUser("faculty1");
		
		assertEquals("facult@woowie.com", f2.getEmail());
		assertEquals(UserType.FACULTY, f2.getType());
		assertEquals("NyOmMMMMy:3", f2.getPassword());
	}
	
	@Test
	public void facultyBorrowing(){
		Faculty f1 = (Faculty) udb.getUser("faculty1");
		
		// create book to borrow
		ItemBuilder ib = new ItemBuilder();
		ib.buildId("accordion");
		ib.buildContent("5-4 swing time in d minor");
		ib.buildName("biscuit town");
		ib.buildPrice(123.45);
		ib.buildType(ItemType.TEXTBOOK);
		
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher = new BookPublisher("chance bird", publisherBooks);
		ldb.addPublisher(publisher);
		ib.buildPublisher(publisher);
		
		Item tb = ib.build();	// built textbook now exists
		//this.tb = (TextBook) tb;
		ldb.addItem(tb);
		
		f1.borrow("accordion");
		assertTrue(f1.getBooksBorrowedList().contains("accordion"));
	}

	@Test
	public void managerTest() {
		Manager m1 = new Manager("user", "pass", "email");
		Students s1 = new Students("user", "pass", "email");
		
		ItemBuilder ib = new ItemBuilder();
		ib.buildId("accordion");
		ib.buildContent("5-4 swing time in d minor");
		ib.buildName("biscuit town");
		ib.buildPrice(123.45);
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("biscuit town");
		BookPublisher publisher = new BookPublisher("chance bird", publisherBooks);
		ldb.addPublisher(publisher);
		
		// create request
		rdb.addRequest(ItemType.BOOK, "id", "superhero songs", "chance bird", RequestType.COURSE_TEACHING, s1);
		
		Set<Request> requests = RequestDataBase.getinstance().getRequests().keySet();
		// add request
		for (Request key : rdb.getRequests().keySet()) {
		    m1.addRequestedItem(key, "all hero songs are in a flat major");
		}
		
		for (Item item : ldb.getItems()) {
			m1.disableItem(item);
            m1.enableItem(item);
            ldb.addItem(item);
		}
		
		Item book = null;
		try {
			book = ldb.getItem("biscuit town");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void approveUserAndVisitorTest() {
		try {
			udb.registerNewUser("mamager:D", "NyOmMMMMy:3", "no@manager.com", UserType.MANAGER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Manager maaaaan = (Manager) udb.getUser("mamager:D");
		
		try {
			udb.registerNewUser("visit", "NyOmMMMMy:3", "visit@no.com", UserType.VISITOR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User visitor = udb.getUser("visit");
		
		udb.approveUser("visit", maaaaan);
		assertEquals("visit@no.com", visitor.getEmail());
		assertEquals(UserType.VISITOR, visitor.getType());
		assertEquals("NyOmMMMMy:3", visitor.getPassword());
		assertEquals("visit@no.com", udb.getUser("visit").getEmail());
	}
	
	@Test
	public void userTestNewsLetters() {
		try {
			udb.registerNewUser("user", "NyOmMMMMy:3", "studn@email.com", UserType.STUDENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User user = udb.getUser("user");
		
		ItemBuilder ib = new ItemBuilder();
		ib.buildId("nyomnyom");
		ib.buildContent("kitchen princess >.<");
		ib.buildName("shoujo beat");
		ib.buildPrice(12.54);
		Set<String> publisherBooks = new HashSet<>();
		publisherBooks.add("Natsumi Ando");
		BookPublisher publisher = new BookPublisher("Natsumi Ando", publisherBooks);
		ldb.addPublisher(publisher);
		ib.buildPublisher(publisher);
		ib.buildType(ItemType.NEWSLETTER);
		
		NewsLetter sb = (NewsLetter) ib.build();
		
		user.Subscribe(sb);
		assertTrue(user.getSubscribed().contains("nyomnyom"));
		
		user.UnSubscribe(sb);
		assertTrue(user.getSubscribed().isEmpty());
	}
	
	@Test
	public void userTest() {
		try {
			udb.registerNewUser("user2", "NyOmMMMMy:3", "studn@email.com", UserType.STUDENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User user2 = udb.getUser("user2");
		assertEquals(user2.getOverDuePayment(), 0);
		assertEquals(Collections.emptySet(), user2.getCourses());
		assertEquals(Collections.emptySet(), user2.getTBhistory());
		assertEquals(0, user2.getLostBookCount());
		
		
		assertEquals(0, user2.getBooksBorrowed().size());
		
		assertEquals(0, user2.getItemsRented().size());
		assertEquals(0, user2.getSubscribed().size());
	}
	
	// user database testing
	@Test
	public void resgisterNewUserFail() {
			assertThrows(IllegalArgumentException.class, () -> {
				udb.registerNewUser("", "NyOmMMMMy:3", "studn@email.com", UserType.STUDENT);
			});
	}
	
	// user database test
	@Test
	public void rejectUser() {
		try {
			udb.registerNewUser("mamager:/", "NyOmMMMMy:3", "no@manager.com", UserType.MANAGER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Manager gorl = (Manager) udb.getUser("mamager:/");
		
		udb.rejectUser("visitlolz", gorl);
		assertTrue(true);
	}
	
	@Test
	public void passWordStrength() {
		assertThrows(IllegalArgumentException.class, () -> {
			udb.checkPasswordStrength("haaaiiii");
		});
		udb.checkPasswordStrength("NyOmMMMMy:3");
	}
	
	@Test
	public void loginTest() {
		try {
			udb.registerNewUser("mamager:/", "NyOmMMMMy:3", "no@manager.com", UserType.MANAGER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Manager gorl = (Manager) udb.getUser("mamager:/");
		
		assertTrue(udb.Login("mamager:/", "NyOmMMMMy:3"));
		assertTrue(!udb.Login("mamager:/", "nyooooo"));
	}
}

