package group7891234.deliverable2.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import group7891234.deliverable2.users.factory.UserFactory;
import group7891234.deliverable2.users.factory.UserType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csvreader.*;

public class UserDataBase {
	private static UserDataBase instance;
	private List<User> users;
	String path = "src/main/resources/users.csv";
	
	private UserDataBase() {
		users = new ArrayList<>();
		//populate users with actual user data
		try {
			populate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void populate() throws Exception {
		  CsvReader reader = new CsvReader(path); // Open the reader
		  try {
		    reader.readHeaders();

		    while (reader.readRecord()) {
		      User user = createUserFromRecord(reader);
		      users.add(user);
		    }
		  } finally {
		    reader.close();  // Ensure closing the reader in a finally block
		  }
	}

	private User createUserFromRecord(CsvReader reader) throws IOException {
	  User user = UserFactory.getUser(
	    reader.get("username"),
	    reader.get("password"),
	    reader.get("email"),
	    UserType.valueOf(reader.get("type"))
	  );

	  user.setBorrowed(parseItemData(reader.get("borrowing")));
	  user.setRenting(parseItemData(reader.get("renting")));
	  
	  switch(UserType.valueOf(reader.get("type"))){
		  case STUDENT:
			  parseStringData(reader.get("courses"));
			  break;
		  case FACULTY:
			  parseStringData(reader.get("courses"));
			  parseStringData(reader.get("tbHistory"));
		  default:
			  break;
	  }

	  return user;
	}
	
	private Set<String> parseStringData(String data){
		if(data.isEmpty()) {
			return Collections.emptySet();
		} 
		
		return new HashSet<>(Arrays.asList(data.split(" ")));
	}

	private Map<LocalDate, Set<String>> parseItemData(String data) {
	  if (data.isEmpty()) {
	    return Collections.emptyMap();
	  }

	  Map<LocalDate, Set<String>> itemMap = new HashMap<>();
	  for (String itemRow : data.split("/")) {
	    String[] parts = itemRow.split(" ");
	    LocalDate date = LocalDate.parse(parts[0]);
	    Set<String> items = new HashSet<>(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
	    itemMap.put(date, items);
	  }
	  return itemMap;
	}
	
	//later issue
	public void registerNewUser(String username, String password, String email, UserType type) {
		if(username.isEmpty() || password.isEmpty() || email.isEmpty())
			throw new IllegalArgumentException("One Of The Fields is Empty");
		User user = UserFactory.getUser(username, password, email, type);	
		users.add(user);
		addUserToCSV(username, password, email, type);
	}
	
	public void addUserToCSV(String username, String password, String email, UserType type) {
		CsvWriter writer = new CsvWriter(path);
		try {
			writer.write(username);
			writer.write(password);
			writer.write(email);
			writer.write(type.toString());
			writer.write("");
			writer.write("");
			writer.write("");
			writer.write("");
			writer.write("");
			writer.write("");
			writer.endRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
	}
	
	public void getFile() {
		  try {
			  CsvReader reader = new CsvReader(path);
			  
		  }catch(Exception e) {
			  
		  }
	}
	
	public static UserDataBase getInstance() {
		if(instance == null) {
			synchronized(UserDataBase.class) {
				if(instance == null) {
					instance = new UserDataBase();
				}
			}
		}
		return instance;
	}
	
	public boolean Login(String username, String password) {
		for (User user : users) {
			if(user.CorrectLogin(username, password)) {
				return true;
			}
		}
		return false;
	}
}
