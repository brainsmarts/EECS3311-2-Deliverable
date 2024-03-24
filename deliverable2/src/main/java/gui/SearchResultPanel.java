package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;

public class SearchResultPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LibraryDataBase database = LibraryDataBase.getInstance();

	public SearchResultPanel(Set<Item> searchResults) {
		setLayout(new GridLayout(3, 1)); 
		
		JButton home = new JButton("Home");
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().changeHomePage();
			}
			
		});
		JPanel searchBar = new JPanel();
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setSize(50,50);
        JTextField searchField = new JTextField("/n /p /t");
        searchField.setPreferredSize(new Dimension(400, 50));
        JButton searchButton = new JButton("Search Button");
        searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					MainUI.getInstance().changeSearchResult(database.search(searchField.getText()));
				}catch(Exception e1) {
					
				}
			}
        	
        });
        searchBar.add(home);
        searchBar.add(searchLabel);
        searchBar.add(searchField);
        searchBar.add(searchButton);
        searchBar.revalidate();
        add(searchBar);
		//add searchbar
		//add back to homepage button
		//add list of objects
		JPanel resultsContainer = new JPanel();
		resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
		JScrollPane resultsScroll = new JScrollPane(resultsContainer);
		for(Item item: searchResults) {
			resultsContainer.add(createItemDisplay(item));
		}
		
		if(searchResults.isEmpty()) {
			resultsContainer.add(new JLabel("No results found"));
		}
		
		resultsContainer.revalidate();
		this.add(resultsScroll);
		
	}
	
	private JPanel createItemDisplay(Item item) {
		JPanel itemDisplay = new JPanel();
		JPanel displayInfo = new JPanel();
		JLabel name = new JLabel(item.getName());
		JLabel id = new JLabel(item.getId());
		JLabel publisher = new JLabel(item.getPublisher().getName());
		JButton viewItem = new JButton("View Item");
		viewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().changeItemViewPanel(item);
			}
			
		});
		displayInfo.setLayout(new BoxLayout(displayInfo, BoxLayout.Y_AXIS));
		
		displayInfo.add(name);
		displayInfo.add(id);
		displayInfo.add(publisher);
		displayInfo.revalidate();
		itemDisplay.add(displayInfo);
		itemDisplay.add(viewItem);
		itemDisplay.revalidate();
		return itemDisplay;
	}
}
