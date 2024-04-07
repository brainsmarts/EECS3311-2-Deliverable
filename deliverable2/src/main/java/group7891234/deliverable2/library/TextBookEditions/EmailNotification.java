package group7891234.deliverable2.library.TextBookEditions;

import group7891234.deliverable2.users.User;

public class EmailNotification implements TextBookNotification{
	User user;
	
	public EmailNotification(User user) {
		this.user = user;
	}

	@Override
	public void update() {
		System.out.println(user.getEmail() + " has been notified of a new edition");
	}
	
}
