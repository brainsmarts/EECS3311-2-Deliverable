package group7891234.deliverable2.request;

import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestDataBase {
	private Map<Request, List<User>> requests;
	private static RequestDataBase instance;
	
	private RequestDataBase() {
		//initialize by reading the cvs file
		//read each line, add the request, then add the users
	}
	
	public static RequestDataBase getinstance() {
		if(instance == null) {
			synchronized(RequestDataBase.class) {
				if(instance == null) {
					instance = new RequestDataBase();
				}
			}
		}
		return instance;
	}
	
	
	//if request does not exist, then create a new request, otherwise add to the request's users to notify
	public void addRequest(ItemType itemType, String name, String id, String publisher,  RequestType type, User user ) {
		for(Map.Entry<Request, List<User>> request: requests.entrySet()) {
			if(request.getKey().compare(name,type)) {
				request.getValue().add(user);
				return;
			}
		}
		
		Request request = new Request(itemType, name, id, publisher, type);
		
		requests.put(request, new ArrayList<User>());
		requests.get(request).add(user);
	}
	
	
	public String getPriority() {
		return "very important :3";
	}
	
}
