package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.BookPublisher;

public class NewsLetter extends Item{

	protected NewsLetter(ItemType type, String id, String name, BookPublisher publisher, String content) {
		super(type, id, name, publisher, content);
		// TODO Auto-generated constructor stub
	}
	
	public void openNewsLetter() {
		//implement when gui is done :3
		return;
	}

	@Override
	public void accepts(ItemVisitor visitor) {
		visitor.visitNewsLetter(this);
	}
}
