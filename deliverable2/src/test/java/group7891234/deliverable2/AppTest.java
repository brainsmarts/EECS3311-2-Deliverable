package group7891234.deliverable2;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.UserType;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
	
	@Test
	public void Testhahahhasdhasdfjaskjdfhasdf() {
		UserDataBase database = UserDataBase.getInstance();
		database.addUserToCSV("TaylorSwift", "tay", "tayswifty@email.com", UserType.FACULTY);
		
	}
	
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
