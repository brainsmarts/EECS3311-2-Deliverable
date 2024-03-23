package group7891234.deliverable2.library.search;

import java.util.Set;

import group7891234.deliverable2.library.item.Item;

public class Search {
	//how to choose between the different strategy's?
	//it could be as simple as putting an a in front
	private SearchStrategy strategy;
	//
	public Set<Item> getSearchResults(String string){
		if(strategy == null) {
			throw new IllegalArgumentException("No Strategy Was Set");
		}
		return strategy.search(string);
	}
	
	public void setStrategy(SearchStrategy strategy) {
		this.strategy = strategy;
	}
	
	
}
