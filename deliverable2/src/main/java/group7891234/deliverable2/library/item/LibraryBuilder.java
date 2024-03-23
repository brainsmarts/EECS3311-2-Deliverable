package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.Publisher;

public interface LibraryBuilder {
	public LibraryBuilder buildType(ItemType type);
	public LibraryBuilder buildId(String id);
	public LibraryBuilder buildName(String name) ;
	public LibraryBuilder buildPublisher(Publisher publisher);
	public LibraryBuilder buildContent(String content);
	public LibraryBuilder buildPrice(double price);
	public Item build();
}
