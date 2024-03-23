package group7891234.deliverable2.library.search;

import java.util.HashSet;
import java.util.Set;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;

public class TypeStrategy implements SearchStrategy{
	
	@Override
	public Set<Item> search(String searchInput) {
		// TODO Auto-generated method stub
		Set<Item> results = new HashSet<>();
		Set<Item> searchThrough = LibraryDataBase.getInstance().getItems();
		for (Item item: searchThrough) {
			if(item.getType().toString().compareToIgnoreCase(searchInput) == 0) {
				results.add(item);
			}
		}
		return results;
	}

}
