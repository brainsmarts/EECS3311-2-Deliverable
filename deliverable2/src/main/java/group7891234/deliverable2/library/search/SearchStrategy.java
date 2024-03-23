package group7891234.deliverable2.library.search;

import java.util.Set;

import group7891234.deliverable2.library.item.Item;

public interface SearchStrategy {
	public Set<Item> search(String searchInput);
}
