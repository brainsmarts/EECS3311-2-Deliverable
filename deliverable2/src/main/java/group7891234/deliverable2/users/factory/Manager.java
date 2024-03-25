package group7891234.deliverable2.users.factory;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.users.User;

public class Manager extends User{

	public Manager(String username, String password, String email) {
		super(username, password, email);
		this.type = UserType.MANAGER;
		// TODO Auto-generated constructor stub
	}
	
	//once the system is logged in, it should keep the user data at all times, that way the button click can just be like
	//user.enableItem();	
	public void enableItem(Item item) {
		 item.enable();
	}
	
	public void disableItem(Item item) {
		item.disable();
	}
		
	//get information from the request, then add make an item and add it to the library database
	public void addRequestedItem(Request request, String content) {
		request.getName();
		ItemBuilder build = new ItemBuilder();
	
		Item requestItem;
		try {
			requestItem = build.buildId(
					request.getId())
					.buildType(request.getItemType())
					.buildName(request.getName())
					.buildPublisher(LibraryDataBase.getInstance().getPublisher(request.getPublisher()))
					.build();
			requestItem.enable();
			
			LibraryDataBase.getInstance().addItem(requestItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
