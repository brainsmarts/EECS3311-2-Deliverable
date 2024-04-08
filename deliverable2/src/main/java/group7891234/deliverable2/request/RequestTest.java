package group7891234.deliverable2.request;
import static org.junit.Assert.*;
import org.junit.*;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.request.RequestDataBase;

public class RequestTest {
	@Test
	public void requestTest1(){
		LibraryDataBase database = LibraryDataBase.getInstance();		
		Request r = new Request(ItemType.BOOK, "Genshin#1", "Tevat travel guide", "Archon", RequestType.SELF_IMPROVEMENT); 
		assertEquals(r.getId(), "Genshin#1");
		
	}
}
