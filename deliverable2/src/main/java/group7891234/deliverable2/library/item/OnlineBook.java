package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.BookPublisher;

public class OnlineBook extends Item{

	protected OnlineBook(ItemType type, String id, String name, BookPublisher publisher, String content) {
		super(type, id, name, publisher, content);
		// TODO Auto-generated constructor stub
	}
	
	public void openOnlineBook() {
		//no idea how to implement this until gui is finished;
	}

	@Override
	public void accepts(ItemVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitOnlineBook(this);
	}

}
