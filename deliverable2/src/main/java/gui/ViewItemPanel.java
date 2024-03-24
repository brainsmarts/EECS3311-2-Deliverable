package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;

public class ViewItemPanel extends JPanel{
	private LibraryDataBase database = LibraryDataBase.getInstance();
	
	public ViewItemPanel(Item item) {
		//View Title
		//View Price
		//2 buttons
		//BorrowBook / Add to Cart
		//Display Item (Cannot if textbook??? or book?????)
	}
	
	private void createSearchBar() {
		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
        
        searchBar.add(homeButton);
        searchBar.add(searchLabel);
        searchBar.add(searchField);
        searchBar.add(searchButton);
        searchBar.revalidate();
        add(searchBar);
	}
}
