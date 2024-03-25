package group7891234.deliverable2;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.Publisher;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.UserType;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws Exception 
     */
	
	@Test
	public void testLibrary() {
		UserDataBase database = UserDataBase.getInstance();
		LibraryDataBase ldatabase = LibraryDataBase.getInstance();
		Item builder = new ItemBuilder()
				.buildType(ItemType.TEXTBOOK)
				.buildId("cheese#7")
				.buildName("Cheening On The Meow")
				.buildPublisher(ldatabase.createPublisher("RatMan"))
				.buildContent("hehehehhehehehe")
				.build();
		ldatabase.addItem(builder);
		/*
		for(Item item : ldatabase.getItems()) {
			System.out.println(item.getId());
		}
		
		/*
		for(Publisher publisher: ldatabase.getPublishers()) {
			System.out.println(publisher.getName());
		}
		*/
	}
	
	public void Testhahahhasdhasdfjaskjdfhasdf() throws Exception {
		UserDataBase database = UserDataBase.getInstance();
		LibraryDataBase ldatabase = LibraryDataBase.getInstance();
		RequestDataBase rdatabase = RequestDataBase.getinstance();
		Set<Request> set = rdatabase.getRequests().keySet();
		Request request1 = null;
		int counter = 0;
		for(Request request: set) {
			System.out.println(request.getId());
			if(counter == 2)
				request1 = request;
			
			counter++;
		}
		rdatabase.rejectRequest(request1);
		
		System.out.println("After Rejection");
		set = rdatabase.getRequests().keySet();
		for(Request request: set) {
			System.out.println(" HAI " + request.getId());
		}
		database.addUserToCSV("TaylorSwift", "tay", "tayswifty@email.com", UserType.FACULTY);
		//type,id,name,price,enabled,publisher,content
		Item builder = new ItemBuilder()
				.buildType(ItemType.BOOK)
				.buildId("chsdsadan")
				.buildName("Cheening On The Meow")
				.buildPublisher(ldatabase.createPublisher("RatMan"))
				.buildContent("hehehehhehehehe")
				.build();
		ldatabase.addItem(builder);
		ldatabase.getItem("cheese#2");
		ldatabase.getItem("cheese#4");
		ldatabase.updateTextBookEditionsFile();
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
