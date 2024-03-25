package group7891234.deliverable2.request;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.Publisher;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemBuilder;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class RequestDataBase {
	private Map<Request, List<String>> requests;
	private static RequestDataBase instance;
	private String path = "src/main/resources/requests.csv";
	
	private RequestDataBase() {
		requests = new HashMap<>();	
		try {
			populate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("An Error Occured Populating Request Data");
		}
		
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
	
	public void approveRequest(Request request, String content) {
		LibraryDataBase database = LibraryDataBase.getInstance();
		Item item;
		try {
			item = new ItemBuilder()
					.buildType(request.getItemType())
					.buildId(request.getId())
					.buildName(request.getName())
					.buildPublisher(database.getPublisher(request.getPublisher()))
					.buildContent(content)
					.build();
			LibraryDataBase.getInstance().addItem(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		notifyUsers(request, "Your request for " + request.getName() + " has been approved");
		removeRequestFromFile(request);
	}
	
	public void rejectRequest(Request request) {
		notifyUsers(request, "Your request for " + request.getName() + " has been rejected");
		removeRequestFromFile(request);
	}
	
	private void removeRequestFromFile(Request request) {
		CsvWriter writer = new CsvWriter(path);
		  try {
			  //read all lines
			  CsvReader reader = new CsvReader(path);
			  List<String[]> lines = new ArrayList<>();
			  String[] line;

			  while(reader.readRecord()) {
				  lines.add(reader.getValues());
			  }
			  
			  reader.close();
			  //if id is found do not write that record
			  for(String[] string : lines) {
				  if(string[2].compareTo(request.getId()) != 0)
					  writer.writeRecord(string);
			  }
			  writer.close();
			  
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		  
		  Iterator<Map.Entry<Request, List<String>>> iterator = requests.entrySet().iterator();
		  while (iterator.hasNext()) {
		      Map.Entry<Request, List<String>> entry = iterator.next();
		      Request temp = entry.getKey();
		      if (temp.getId().equals(request.getId())) {
		          iterator.remove(); // Remove the entry from the map
		      }
		  }
		 
		  return;
	}
	
	public void populate() throws FileNotFoundException {
		CsvReader reader = new CsvReader(path); // Open the reader
		  try {
		    reader.readHeaders();

		    while (reader.readRecord()) {
		      createRequestFromRecord(reader);
		    }
		    reader.close();
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
	}
		  
	public Map<Request, List<String>> getRequests(){
		return requests;
	}
	
	//if request does not exist, then create a new request, otherwise add to the request's users to notify
	public void addRequest(ItemType itemType, String name, String id, String publisher,  RequestType type, User user ) {
		for(Entry<Request, List<String>> request: requests.entrySet()) {
			if(request.getKey().compare(name,type)) {
				request.getValue().add(user.getUserName());
				return;
			}
		}
		
		Request request = new Request(itemType, name, id, publisher, type);
		
		requests.put(request, new ArrayList<String>());
		requests.get(request).add(user.getUserName());
	}
	
	public void addRequest(ItemType itemType, String id,String name, String publisher,  RequestType type, String[] users ) {
		Request request = new Request(itemType, id, name, publisher, type);
		requests.put(request, new ArrayList<String>());
	}
		
	public String getPriority() {
		return "very important :3";
	}
	
	private void notifyUsers(Request request, String status) {
		List<String> users = requests.get(request);
		String email = "";
		if(users == null){
			return;
		}
		for(String username: users) {
			email = UserDataBase.getInstance().getUser(username).getEmail();
			//pretend we use status to email
			System.out.println("To: " + email + "\n" + status);
		}
	}
	private void addRequestToFile(Request request, User user) {
		
	}
	
	private void createRequestFromRecord(CsvReader reader) {
		Request request = null;
		try {
			ItemType type = ItemType.valueOf(reader.get("itemType"));
			String name = reader.get("name");
			String id = reader.get("id");
			String publisher = reader.get("publisher");
			RequestType requestType = RequestType.valueOf(reader.get("requestType"));
			
			addRequest(type,id ,name,publisher,  requestType,  reader.get("users").split(" "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
