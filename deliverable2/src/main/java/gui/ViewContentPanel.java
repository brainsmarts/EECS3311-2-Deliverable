package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Book;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.library.item.ItemVisitor;
import group7891234.deliverable2.library.item.NewsLetter;
import group7891234.deliverable2.library.item.OnlineBook;
import group7891234.deliverable2.library.item.TextBook;

public class ViewContentPanel extends JPanel implements ItemVisitor{
	
	private LibraryDataBase database = LibraryDataBase.getInstance();
	
	public ViewContentPanel() {
		//create search bar
		createSearchBar();
		//create content panel
	}
	
	@Override
	public void visitBook(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitOnlineBook(OnlineBook book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitNewsLetter(NewsLetter news) {
		try {
            Desktop.getDesktop().browse(new URI(news.getContent()));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
	}

	@Override
	public void visitTextBook(TextBook book) {
		// TODO Auto-generated method stub
	}
	
	private void createSearchBar() {
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
	}
}
