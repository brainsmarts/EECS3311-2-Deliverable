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
import group7891234.deliverable2.users.User;
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
		RequestDataBase rdatabase = RequestDataBase.getinstance();
		User test = database.getUser("TaylorSwift");
		test.borrow("cheese#7");
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
	
	
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
