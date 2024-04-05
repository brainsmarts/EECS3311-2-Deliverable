package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.BookPublisher;
import group7891234.deliverable2.library.LibraryDataBase;


public class ItemBuilder implements LibraryBuilder{
	private ItemType type;
	private double price;
	private String id;
	private String name;
	private BookPublisher publisher;
	private String content;
	@Override
	public LibraryBuilder buildType(ItemType type) {
		this.type = type;
		return this;
	}

	@Override
	public LibraryBuilder  buildId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
		return this;
	}

	@Override
	public LibraryBuilder  buildName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}

	public LibraryBuilder  buildPublisher(BookPublisher publisher) {
		this.publisher = publisher;
		return this;
	}

	@Override
	public LibraryBuilder buildContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public Item build() {
		// TODO Auto-generated method stub
		Item item;
		switch(type) {
		case BOOK: 
			item = new Book(type,id,name,publisher,content);
			break;
		case ONLINEBOOK:
			item = new OnlineBook(type,id,name,publisher,content);
			break;
		case NEWSLETTER:
			item = new NewsLetter(type,id,name,publisher,content);
			break;
		case TEXTBOOK:
			item = new TextBook(type,id,name,publisher,content);
			break;
		default:
			throw new IllegalArgumentException("InvalidType:" + type);
		}
		
		item.setPrice(price);
		return item;
	}

	@Override
	public LibraryBuilder buildPrice(double price) {
		// TODO Auto-generated method stub
		this.price = price;
		return this;
	}
}
