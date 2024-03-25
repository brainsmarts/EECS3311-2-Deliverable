package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.library.item.ItemVisitor;
import group7891234.deliverable2.library.item.NewsLetter;
import group7891234.deliverable2.library.item.OnlineBook;
import group7891234.deliverable2.library.item.TextBook;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.UserDataBase;

public class ViewItemPanel extends JPanel implements ItemVisitor{
	private LibraryDataBase database = LibraryDataBase.getInstance();
	private UserDataBase udatabase = UserDataBase.getInstance();
	private User user;
	JButton add;
	
	public ViewItemPanel(Item item, User user) {
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.user = user;
		createSearchBar();
		JLabel name = new JLabel("Name: " + item.getName());
		name.setSize(300, 50);;
		//View Title
		JLabel price = new JLabel("Price" + Double.toString(item.getPrice()));
		add = new JButton("Name to be changed");
		price.setSize(300, 50);;
		//View Price
		//go to library data and add to the borrow list, 
		//go to user and add to borrowing list
		JButton getContent = new JButton("View Content");
		getContent.setSize(300, 50);;
		getContent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainUI.getInstance().changeContentPanel(item);
			}
			
		});
		add.setSize(600, 50);
		JPanel contentPanel = new JPanel();
		contentPanel.setSize(300, 600);
		contentPanel.setLayout(new GridLayout(4,1));
		//uses item visitor class to do it so no cases are needed
		//2 buttons
		//BorrowBook / Add to Cart
		//Display Item (Cannot if textbook??? or book?????)
		contentPanel.add(name);
		contentPanel.add(price);
		contentPanel.add(add);
		contentPanel.add(getContent);
		contentPanel.revalidate();
		add(contentPanel, BorderLayout.SOUTH);
		if(!item.isEnabled()){
			setLayout(new GridLayout(2,1));
			JPanel disabledPanel = new JPanel();
			disabledPanel.add(name);
			disabledPanel.add(new JLabel("Currently Unavailable"));
			disabledPanel.revalidate();
			add(disabledPanel);
			contentPanel.setVisible(false);
			contentPanel.revalidate();
			revalidate();
		}
		
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
        //searchBar.setLayout(new GridLayout(1,4));
        
        searchBar.add(homeButton);
        searchBar.add(searchLabel);
        searchBar.add(searchField);
        searchBar.add(searchButton);
        searchBar.revalidate();
        add(searchBar);
	}

	@Override
	public void visitBook(Book book) {
		if(book.getPrice() != 0) {
			suddenlyPAYMENT(book);
			return;
		}
		if(user.getBooksBorrowedList().contains(book)) {
			add.setText("Already Borrowing/Rented");
			add.revalidate();
			return;
		}
		add.setText("Borrow");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					user.borrow(book.getId());
					add.setText("Already Borrowing/Rented");
					add.removeActionListener(this);
					add.revalidate();
				}catch(Exception t) {
					t.printStackTrace();
				}
				
			}
			
		});
		revalidate();
	}

	@Override
	public void visitOnlineBook(OnlineBook book) {
		// TODO Auto-generated method stub
		add.setVisible(false);
		revalidate();
	}

	@Override
	public void visitNewsLetter(NewsLetter news) {
		// TODO Auto-generated method stub
		if(user.getSubscribed().contains(news)) {
			add.setText("Subscribed");
			return;
		}
		add.setText("Subscribe");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user.Subscribe(news);
			}
			
		});
		revalidate();
	}

	@Override
	public void visitTextBook(TextBook book) {
		// TODO Auto-generated method stub
		if(book.getPrice() != 0) {
			suddenlyPAYMENT(book);
			return;
		}
		if(user.getBooksBorrowedList().contains(book)) {
			add.setText("Already Borrowing/Rented");
			add.revalidate();
			return;
		}
		add.setText("Borrow");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					user.borrow(book.getId());
					add.setText("Already Borrowing/Rented");
					add.removeActionListener(this);
					add.revalidate();
				}catch(Exception t) {
					t.printStackTrace();
				}
			}
		});
		add.revalidate();
	}

	public void suddenlyPAYMENT(Item item) {
		add.setText("BUY");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainUI.getInstance().changeCheckoutPage(item);
			}
			
		});
		add.revalidate();
	}
}
