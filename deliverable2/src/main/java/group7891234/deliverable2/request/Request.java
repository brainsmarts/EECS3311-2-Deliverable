package group7891234.deliverable2.request;

import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.users.User;

public class Request {
	public RequestType getType() {
		return type;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPublisher() {
		return publisher;
	}

	private RequestType type;
	private ItemType itemType;
	private String id;
	private String name;
	private String publisher;
	
	public Request(ItemType itemType, String id, String name, String publisher, RequestType type){
		this.itemType = itemType;
		this.name = name;
		this.publisher = publisher;
		this.type = type;
		this.id = id;
	}
	
	public boolean compare(String name, RequestType type) {
		return (name.compareTo(this.id) == 0 && type == this.type);
	}
	
}
