package group7891234.deliverable2.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import group7891234.deliverable2.users.factory.Faculty;
import group7891234.deliverable2.users.factory.Manager;
import group7891234.deliverable2.users.factory.Students;
import group7891234.deliverable2.users.factory.UserFactory;
import group7891234.deliverable2.users.factory.UserType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csvreader.*;

public class UserDataBase {
	private static UserDataBase instance;
	private List<User> users;
	private List<String[]> pending_users;
	String path = "src/main/resources/users.csv";
	String approve_path = "src/main/resources/approve_user.csv";
	
	private UserDataBase() {
		users = new ArrayList<>();
		pending_users = new ArrayList<>();
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
		    reader.close();
		    
		    reader = new CsvReader(approve_path);
		    reader.readHeaders();
		    while(reader.readRecord()) {
		    	pending_users.add(reader.getValues());
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
			  ((Students)user).setCourses(parseStringData(reader.get("courses")));
			  //((Student)user).setCourses(parseStringData(reader.get("courses")));
			  break;
		  case FACULTY:
			  ((Faculty)user).setCourses(parseStringData(reader.get("courses")));
			  ((Faculty)user).setTextBookHistory(parseStringData(reader.get("tbHistory")));
			  break;
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
		throw new IllegalArgumentException("User Not Found " + username);
	}
	
	private Set<String> parseStringData(String data){
		if(data.isEmpty()) {
			//System.out.println("Empty Subcribe");
			return new HashSet<>();
		} 
		Set<String> set = new HashSet<>(Arrays.asList(data.split(" ")));
		return set;
	}

	private Map<LocalDate, Set<String>> parseItemData(String data) {
	  Map<LocalDate, Set<String>> itemMap = new HashMap<>();
	  if(data.isEmpty()) {
		  return itemMap;
	  }
	  for (String itemRow : data.split("/")) {
	    String[] parts = itemRow.split(" ");
	    LocalDate date = LocalDate.parse(parts[0]);
	    Set<String> items = new HashSet<>(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
	    itemMap.put(date, items);
	  }
	  return itemMap;
	}
	
	//later issue
	public void registerNewUser(String username, String password, String email, UserType type) throws Exception {
		if(username.isEmpty() || password.isEmpty() || email.isEmpty())
			throw new IllegalArgumentException("One Of The Fields is Empty");
		
		checkPasswordStrength(password);
		
		User user = UserFactory.getUser(username, password, email, type);	
		users.add(user);
		user.setBorrowed(new HashMap<>());
		user.setRenting(new HashMap<>());
		user.setSubscribed(Collections.emptySet()); 
		if(type != UserType.STUDENT && type != UserType.FACULTY && type != UserType.NON_FACULTY)
			addUserToCSV(username, password, email, type);
		else {
			String[] user_request = {username,password,email,type.toString()};
			getPending_users().add(user_request);
			updateApproveFile();
		}
	}
	
	public void approveUser(String username, Manager manager) {
		//find in list
		int counter = 0;
		
		for(String[] userR : getPending_users()) {
			System.out.println(userR[0] + " " + username);
			if (userR[0].compareTo(username) == 0){
				System.out.println("Manager " + manager.getUserName() + " has approved");
				User user = UserFactory.getUser(username, userR[1], userR[2], UserType.valueOf(userR[3]));	
				users.add(user);
				break;
			}
			counter++;
		}
		if(counter == pending_users.size()) {
			return;
		}
		pending_users.remove(counter);
		updateUsersFile();
		updateApproveFile();
	}
	
	public void rejectUser(String username, Manager manager) {
		int counter = 0;
		if(pending_users.isEmpty())
			return;
		
		for(String[] userR : getPending_users()) {
			if(userR[0].compareTo(username) == 0) {
				break;
			}
			counter++;
		}
		if(counter == pending_users.size()) {
			return;
		}
		pending_users.remove(counter);
		
		//remove from cvs file by simply updating
		updateApproveFile();
	}
	
	public void updateUsersFile() {
		try {
			CsvReader reader = new CsvReader(approve_path);
			String[] header = {
				    "username",         // Username
				    "password",         // Password
				    "email",// Email
				    "type",          // Type
				    "borrowing",        // Borrowing
				    "renting",          // Renting
				    "subscribed",       // Subscribed
				    "lostBookCount",    // LostBookCount
				    "courses",          // Courses
				    "tbHistory"         // TbHistory
				};
			reader.close();
			
			CsvWriter writer = new CsvWriter(path);
			String[] record = new String[10];
			writer.writeRecord(header);
			for(User user : users) {
				String borrowing = "";
				String renting = "";
				for(Map.Entry<LocalDate, Set<String>> entry : user.getBooksBorrowed().entrySet()) {
					borrowing += entry.getKey().toString() + " ";
					for(String string: entry.getValue()) {
						borrowing += string + " ";
					}
				}
				for(Map.Entry<LocalDate, Set<String>> entry : user.getBooksBorrowed().entrySet()) {
					renting += entry.getKey().toString();
					for(String string: entry.getValue()) {
						borrowing += string + " ";
					}
				}
				record[0] = user.getUserName();
				record[1] = user.getPassword();
				record[2] = user.getEmail();
				record[3] = user.getType().toString();
				record[4] = borrowing;
				record[5] = renting;
				record[6] =  String.join(" ", user.getSubscribed());
				record[7] = Integer.toString( user.getLostBookCount());
				record[8] = String.join(" ", user.getCourses());
				record[9] = String.join(" ", user.getTBhistory());
				writer.writeRecord(record);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void updateApproveFile() {
		try {
			CsvReader reader = new CsvReader(approve_path);
			String[] headers = {"username","password","email","type"};
			reader.close();
			
			CsvWriter writer = new CsvWriter(approve_path);
			writer.writeRecord(headers);
			for(String [] record : this.pending_users)
				writer.writeRecord(record);
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
        if (password.length() < 8 || !containsUpperCase(password) || !containsLowerCase(password) || !containsDigit(password)) 
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
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

	public List<String[]> getPending_users() {
		return pending_users;
	}
}
