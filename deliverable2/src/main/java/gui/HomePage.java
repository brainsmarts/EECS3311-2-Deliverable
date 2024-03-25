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
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import javax.swing.ScrollPaneConstants;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.NewsLetter;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.factory.Faculty;
import group7891234.deliverable2.users.factory.Students;
import group7891234.deliverable2.users.factory.UserType;

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
	private JLabel owed;

	public HomePage(User user) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.user = user;
        owed = new JLabel();
        if(user.getOverDuePayment() == 0)
        	owed.setVisible(false);
        else {
        	owed.setText("You now owe $" + user.getOverDuePayment() + " in late fees");
        }
      

        // Create and add search bar
        createSearchBar();
        UserSpecifics();
        this.add(owed);
        // Create and add book list
        createBorrowedList();
       
        // Create and add open website newsletters
        add(new JLabel("Subscribed"));
        createNewsLetterList();
        
        revalidate();
    }
	
	private void createHistoryList() {
		JPanel historyPanel = new JPanel();
		Set<String> history = user.getTBhistory();
		historyPanel.add(new JLabel("Previously Viewed"));
		for(String string: history) {
			historyPanel.add(new JLabel(string));
		}
		add(historyPanel);
	}	

	private void createTextBookList() {
		//get textbooks from uds
		JPanel textbookPanel = new JPanel();
		Set<String> courses = user.getCourses();
		for(String string: courses) {
			JPanel coursePanel = new JPanel();
			coursePanel.setLayout(new GridLayout(2,1));
			JLabel courseLabel = new JLabel(string);
			JButton onlineButton = new JButton(string + "TextBook");
			try {
				Item item = LibraryDataBase.getInstance().getSeriesOnlineBook(string);
				System.out.println(item.getId());
				onlineButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						MainUI.getInstance().changeContentPanel(item);
					}
					
				});
			}catch(Exception e) {
				onlineButton.setText("Online TextBook Not Found");
			}
			coursePanel.add(courseLabel);
			coursePanel.add(onlineButton);
			textbookPanel.add(coursePanel);
			coursePanel.revalidate();
		}
		textbookPanel.add(new JLabel("TextBooks"));
		
		textbookPanel.revalidate();
		
		add(textbookPanel);
	}
	

	private void createManagementPanel() {
		JPanel managerPanel = new JPanel();
		JButton itemRequest = new JButton("Item Requests");
		JButton itemManage = new JButton("Manage Items");
		JButton manageUsers = new JButton("Manage Users");
		managerPanel.add(itemRequest);
		managerPanel.add(itemManage);
		managerPanel.add(manageUsers);
		managerPanel.revalidate();
		add(managerPanel);
		
		itemRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().changeRequestManagePage();
			}
		});
		itemManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().changeItemManagePage();;
			}
		});
		manageUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().changeUserRequestManagePage();;
			}
		});
		//manage item requests
		
		//manage items
		//manage user request
	}
	
	private void createBorrowedList() {
		 Map<LocalDate, Set<String>> userItems = user.getBooksBorrowed();
	        JPanel listContainer = new JPanel();
	        JScrollPane scrollPane = new JScrollPane(listContainer);

	        
	        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.PAGE_AXIS));
	        listContainer.setPreferredSize(new Dimension(300, 600));
	        
	        for (Map.Entry<LocalDate, Set<String>> entry : userItems.entrySet()) {
	        	for(String string: entry.getValue()) {
	        		try {
						listContainer.add(getBorrowedItemDisplay(database.getItem(string), entry.getKey()));
					} catch (Exception e) {
						System.out.println("Book not found");
					}
	        	}
	        }
	        
	        listContainer.revalidate();

	        add(scrollPane);
	}
	
	private void createSearchBar() {
		JButton logOut = new JButton("Log Out");
		logOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainUI.getInstance().logOut();
			}
			
		});
		JButton requestButton = new JButton("Make a Request");
		requestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainUI.getInstance().changeRequestPage();
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
        
        searchBar.add(logOut);
        searchBar.add(requestButton);
        searchBar.add(searchLabel);
        searchBar.add(searchField);
        searchBar.add(searchButton);
        searchBar.revalidate();
        add(searchBar);
	}
    
	private void createNewsLetterList() {
		List<String> list = user.getSubscribed();
		JPanel listContainer = new JPanel();
		for(String id: list) {
			
			try {
				JPanel panel = getNewsLetterDisplayItem(database.getItem(id));
				listContainer.add(panel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		listContainer.revalidate();
		
		add(listContainer);
		
	}
	
	private JPanel getNewsLetterDisplayItem(Item item) {
		System.out.println("News Created");
		JPanel itemDisplay = new JPanel();
		itemDisplay.setLayout(new GridLayout(4,1));
		JLabel name = new JLabel(item.getName());
		JLabel idLabel = new JLabel("ID: " + item.getId());
        JLabel publisherLabel = new JLabel("Publisher: " + item.getPublisher().getName());
        
        JPanel buttonLayout = new JPanel();
        JButton viewNewsLetter = new JButton("View NewsLetter");
        viewNewsLetter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openWebsite(item.getContent());
			}
        	
        });
        JButton unsubscribe = new JButton("Unsubscribe");
        unsubscribe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user.UnSubscribe((NewsLetter)item);
			}
        	
        });
        buttonLayout.add(viewNewsLetter);
        buttonLayout.add(unsubscribe);
        
        itemDisplay.add(name);
        itemDisplay.add(idLabel);
        itemDisplay.add(publisherLabel);
        itemDisplay.add(buttonLayout);
        itemDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        itemDisplay.setSize(300, 50);
        itemDisplay.revalidate();
		return itemDisplay;
	}
	
	private JPanel getBorrowedItemDisplay(Item item, LocalDate date) {
		JPanel itemDisplay = new JPanel();
		itemDisplay.setLayout(new GridLayout(5,1));
		JLabel name = new JLabel(item.getName());
		JLabel idLabel = new JLabel("ID: " + item.getId());
        JLabel publisherLabel = new JLabel("Publisher: " + item.getPublisher().getName());
        JLabel dueDateLabel = new JLabel("Due Date: " + date);
        
        itemDisplay.add(name);
        itemDisplay.add(idLabel);
        itemDisplay.add(publisherLabel);
        itemDisplay.add(dueDateLabel);
        itemDisplay.add(getWarning(date));
        itemDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        itemDisplay.setSize(300, 50);
        itemDisplay.revalidate();
		return itemDisplay;
	}
	
	private JLabel getWarning(LocalDate date) {
		JLabel warning = new JLabel("Label"); 
		long days = LocalDate.now().until(date, ChronoUnit.DAYS);
		System.out.println(days);
		if(date.isBefore(LocalDate.now()))
		if(days < 5) {
			if(days >= 0) {
				warning.setText("You have " + days + " days left to return your book");
			} else{
				warning.setText("You are " + days + " days overdue to return your book");
			}
		}else {
			warning.setVisible(false);
		}
		warning.revalidate();
		return warning;	
	}

    private void openWebsite(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void UserSpecifics() {
    	UserType type = user.getType();
    	switch(type) {
    		case MANAGER :
    			createManagementPanel();
    			break;
    		case STUDENT:
    			System.out.println("Student Located");
    			createTextBookList();
    			break;
    		case FACULTY:
    			createTextBookList();
    			createHistoryList();
    			break;
    		default:
    	}
    	//if student add course specific textbooks
    	//if faculty add textbookhistory too
    	//if manager add manage requests button
    	
    }
}
