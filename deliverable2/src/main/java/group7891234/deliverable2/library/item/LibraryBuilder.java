package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.BookPublisher;

public interface LibraryBuilder {
	public LibraryBuilder buildType(ItemType type);
	public LibraryBuilder buildId(String id);
	public LibraryBuilder buildName(String name) ;
	public LibraryBuilder buildPublisher(BookPublisher publisher);
	public LibraryBuilder buildContent(String content);
	public LibraryBuilder buildPrice(double price);
	public Item build();
}
