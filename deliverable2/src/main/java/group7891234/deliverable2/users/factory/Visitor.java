package group7891234.deliverable2.users.factory;

import group7891234.deliverable2.users.User;

public class Visitor extends User{

	public Visitor(String username, String password, String email) {
		super(username, password, email);
		type = UserType.VISITOR;
		// TODO Auto-generated constructor stub
	}

}
