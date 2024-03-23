package group7891234.deliverable2.library.item;

import group7891234.deliverable2.library.Publisher;

public abstract class Item {


	private ItemType type;
	private double price;
	private String id;
	private String name;
	private boolean enabled;
	private Publisher publisher;
	private String content;
	
	protected Item(ItemType type, String id, String name, Publisher publisher, String content) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.content = content;
		enabled = false;
		price = 0.00;
		
	}
	
	//because price may change
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
	public abstract void accepts();
	
	public ItemType getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public String getContent() {
		return content;
	}
}
