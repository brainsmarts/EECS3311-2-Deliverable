package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.users.User;

public class HomePage extends JPanel{
	/*
	 * Requires
	 * Search Bar
	 * List of books user is renting/borrowing
	 * Warning prompt for any book not returned
	 * Open a website with a list of newsletters
	 * 
	 * */
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LibraryDataBase database = LibraryDataBase.getInstance();
	private User user;

	public HomePage(User user) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Create and add search bar
        JButton requestButton = new JButton("Make a Request");
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
        
        searchBar.add(requestButton);
        searchBar.add(searchLabel);
        searchBar.add(searchField);
        searchBar.add(searchButton);
        searchBar.revalidate();
        add(searchBar);

        // Create and add book list
        Map<LocalDate, Set<String>> userItems = user.getBooksBorrowed();
        JPanel listContainer = new JPanel();
        JScrollPane scrollPane = new JScrollPane(listContainer);

        
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.PAGE_AXIS));
        listContainer.setPreferredSize(new Dimension(300, 600));
        
        for (Map.Entry<LocalDate, Set<String>> entry : userItems.entrySet()) {
        	for(String string: entry.getValue()) {
        		try {
					listContainer.add(getBorrowedItemDisplay(database.getItem(string), entry.getKey()));
					System.out.println("Book Found");
				} catch (Exception e) {
					System.out.println("Book not found");
				}
        	}
        }
        
        listContainer.revalidate();

        add(scrollPane);
 
        // Create and add open website newsletters
        JButton openWebsiteButton = new JButton("Open Website");
        openWebsiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite("https://google.com");
            }
        });
        add(openWebsiteButton);
         revalidate();
    }
    
	private JPanel getBorrowedItemDisplay(Item item, LocalDate date) {
		JPanel itemDisplay = new JPanel();
		itemDisplay.setLayout(new GridLayout(4,1));
		JLabel name = new JLabel(item.getName());
		JLabel idLabel = new JLabel("ID: " + item.getId());
        JLabel publisherLabel = new JLabel("Publisher: " + item.getPublisher().getName());
        JLabel dueDateLabel = new JLabel("Due Date: " + date);
        
        itemDisplay.add(name);
        itemDisplay.add(idLabel);
        itemDisplay.add(publisherLabel);
        itemDisplay.add(dueDateLabel);
        itemDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        itemDisplay.setSize(300, 50);
        itemDisplay.revalidate();
		return itemDisplay;
	}
	
    private void openWebsite(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void UserSpecifics() {
    	//if student add course specific textbooks
    	//if faculty add textbookhistory too
    	//if manager add manage requests button
    }
}
