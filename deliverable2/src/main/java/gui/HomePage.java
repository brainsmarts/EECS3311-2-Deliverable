package gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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

	public HomePage() {
        setLayout(new BorderLayout());

        // Create and add search bar
        JTextField searchBar = new JTextField();
        add(searchBar, BorderLayout.NORTH);

        // Create and add book list
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        JScrollPane bookScrollPane = new JScrollPane(bookList);
        add(bookScrollPane, BorderLayout.CENTER);

        // Create and add open website button
        JButton openWebsiteButton = new JButton("Open Website");
        openWebsiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite("https://google.com");
            }
        });
        add(openWebsiteButton, BorderLayout.SOUTH);
         
    }
    
    private void openWebsite(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
