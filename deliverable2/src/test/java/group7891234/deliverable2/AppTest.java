package group7891234.deliverable2;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.Publisher;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
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
	public void Testhahahhasdhasdfjaskjdfhasdf() throws Exception {
		UserDataBase database = UserDataBase.getInstance();
		LibraryDataBase ldatabase = LibraryDataBase.getInstance();
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
		ldatabase.updateTextBookEditionsFile("cheese");
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
