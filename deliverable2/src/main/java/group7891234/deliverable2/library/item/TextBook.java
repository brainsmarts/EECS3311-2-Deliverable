package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.BookPublisher;

public class TextBook extends Item{

	protected TextBook(ItemType type, String id, String name, BookPublisher publisher, String content) {
		super(type, id, name, publisher, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accepts(ItemVisitor visitor ) {
		// TODO Auto-generated method stub
		visitor.visitTextBook(this);
	}

}
