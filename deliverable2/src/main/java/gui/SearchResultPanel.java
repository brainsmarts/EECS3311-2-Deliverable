package gui;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;

import group7891234.deliverable2.library.item.Item;

public class SearchResultPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchResultPanel() {
		setLayout(new GridLayout(3, 1)); 
		
		
		//add searchbar
		//add back to homepage button
		//add list of objects
		
	}
	
	protected void displaySearchResult(Set<Item> searchResults) {
		
	}
	
	private JPanel createItemDisplay(Item item) {
		//name
		//publisher
		//view item button
		return null;
	}
}
