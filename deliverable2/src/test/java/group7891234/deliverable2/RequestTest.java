package group7891234.deliverable2;
import static org.junit.Assert.*;
import org.junit.*;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.request.RequestType;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;

public class RequestTest {
	@Test
	public void requestTest1(){
	
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getId(), "Genshin#1");
		
		
	}
	@Test
	public void requestTest2(){	
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getItemType(), ItemType.BOOK);
		
	}
	
	@Test
	public void requestTest3(){	
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getName(), "Tevat travel guide");
		
	}
	@Test
	public void requestTest4(){		
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getPublisher(), "Archon");
		
	}
	@Test
	public void requestTest5(){		
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getPublisher(), "Archon");
	}
	@Test
	public void requestTest6(){		
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getType(), RequestType.SELF_IMPROVEMENT);
	}
	@Test
	public void requestTest7(){		
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		Request r2 = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertNotEquals(r, r2);
	}
	
	@Test
	public void requestTest8() {
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertFalse(r.compare("cat#1", RequestType.SELF_IMPROVEMENT ));
	}
	
	@Test
	public void requestDBTest1() {
		RequestDataBase rdb = RequestDataBase.getinstance();
		Request r = new Request(ItemType.ONLINEBOOK, "Weakness#2", "Cry for help", "Olaf the snowman", RequestType.SELF_IMPROVEMENT );
		rdb.approveRequest(r, "group therapy session");
		
	}
	@Test
	public void requestDBTest2() {
		RequestDataBase rdb = RequestDataBase.getinstance();
		Request r = new Request(ItemType.ONLINEBOOK, "Weakness#2", "Cry for help", "Olaf the snowman", RequestType.SELF_IMPROVEMENT );
		rdb.rejectRequest(r);
		
	}
	
	@Test
	public void requestDBTest3() {
		RequestDataBase rdb = RequestDataBase.getinstance();
		RequestDataBase rdb2 = RequestDataBase.getinstance();
		Request r = new Request(ItemType.ONLINEBOOK, "Weakness#2", "Cry for help", "Olaf the snowman", RequestType.SELF_IMPROVEMENT );
		//System.out.print(rdb.getRequests().toString());
		assertEquals(rdb.getRequests(), rdb2.getRequests());
	}
	@Test
	public void requestDBTest4() {
		RequestDataBase rdb = RequestDataBase.getinstance();
		Request r = new Request(ItemType.ONLINEBOOK, "Weakness#2", "Cry for help", "Olaf the snowman", RequestType.SELF_IMPROVEMENT );
		assertEquals(rdb.getPriority(), "very important :3");
	}
	@Test
	public void requestDBTest5() {
		RequestDataBase rdb = RequestDataBase.getinstance();
		Request r = new Request(ItemType.ONLINEBOOK, "Weakness#2", "Cry for help", "Olaf the snowman", RequestType.SELF_IMPROVEMENT );
		UserDataBase database = UserDataBase.getInstance();
		User test = database.getUser("user");
		rdb.addRequest(ItemType.NEWSLETTER, "BBC", "CBB", "The united kingdom", RequestType.COURSE_TEACHING, test);
		rdb.rejectRequest(r);
	}
	

	
	
	
}
