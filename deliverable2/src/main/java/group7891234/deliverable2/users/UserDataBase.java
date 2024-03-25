package group7891234.deliverable2.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import group7891234.deliverable2.users.factory.Manager;
import group7891234.deliverable2.users.factory.UserFactory;
import group7891234.deliverable2.users.factory.UserType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csvreader.*;

public class UserDataBase {
	private static UserDataBase instance;
	private List<User> users;
	private List<String[]> users_to_approve;
	String path = "src/main/resources/users.csv";
	String approve_path = "src/main/resources/approve_user.csv";
	
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
	  user.setSubscribed(parseStringData(reader.get("subscribed")));
	  
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
	
	public User getUser(String username) {
		for(User user: users) {
			if(user.getUserName().compareTo(username) == 0) {
				return user;
			}
		}
		throw new IllegalArgumentException("User Not Found");
	}
	
	private Set<String> parseStringData(String data){
		if(data.isEmpty()) {
			//System.out.println("Empty Subcribe");
			return Collections.emptySet();
		} 
		Set<String> set = new HashSet<>(Arrays.asList(data.split(" ")));
		return set;
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
		
		checkPasswordStrength(password);
		User user = UserFactory.getUser(username, password, email, type);	
		users.add(user);
		user.setBorrowed(Collections.emptyMap());
		user.setRenting(Collections.emptyMap());
		user.setSubscribed(Collections.emptySet()); 
		if(type != UserType.STUDENT && type != UserType.FACULTY)
			addUserToCSV(username, password, email, type);
	}
	
	public void approveUser(String username, Manager manager) {
		//find in list
		//add to users list
		//add to csv
	}
	
	public void addUserToCSV(String username, String password, String email, UserType type) {
		CsvWriter writer = getFileWriter();
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
	
	public CsvWriter getFileWriter() {
		CsvWriter writer = new CsvWriter(path);
		  try {
			  CsvReader reader = new CsvReader(path);
			  List<String[]> lines = new ArrayList<>();
			  String[] line;
			  
			  while(reader.readRecord()) {
				  lines.add(reader.getValues());
			  }
			  for(String[] string : lines) {
				  writer.writeRecord(string);
			  }
			  
		  }catch(Exception e) {
			  
		  }
		 
		  return writer;
	}
	
	public List<String[]> getFileContent() {

		List<String[]> lines = new ArrayList<>();
		  try {
			  CsvReader reader = new CsvReader(path);
			  String[] line;
			  
			  while(reader.readRecord()) {
				  lines.add(reader.getValues());
			  }
			  
		  }catch(Exception e) {
			  
		  }
		  return lines;
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
	
	public void updateFileData() {
		
	}
	
    public static void checkPasswordStrength(String password) throws IllegalArgumentException {
        // Check if the password meets the criteria for being strong enough
        if (password.length() < 8 || !containsUpperCase(password) || !containsLowerCase(password) || !containsDigit(password)) {
            // Throw an IllegalArgumentException if the password is not strong enough
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
        }
    }

    private static boolean containsUpperCase(String password) {
        return !password.equals(password.toLowerCase());
    }

    private static boolean containsLowerCase(String password) {
        return !password.equals(password.toUpperCase());
    }

    private static boolean containsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
