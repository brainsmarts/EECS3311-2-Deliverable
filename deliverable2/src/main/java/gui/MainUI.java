package gui;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.users.User;

public class MainUI extends JFrame{

	/**
	 * 
	 */
	private static MainUI instance;
	protected static User user = null;
	private static final long serialVersionUID = 1L;
	private JPanel loginPanel;
	private HomePage homepagePanel;
	private SearchResultPanel searchPanel;
	private ViewContentPanel contentPanel;
	
	public static void main(String[] args) {
		JFrame frame = MainUI.getInstance();
		
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private MainUI() {
		loginPanel = new LoginPanel();
		this.setContentPane(loginPanel);
	}

	protected static MainUI getInstance() {
		// TODO Auto-generated method stub
		if(instance == null) {
			synchronized(MainUI.class) {
				if(instance == null) {
					instance = new MainUI();
				}
			}
		}
		return instance;
	}
	
	protected void changeSearchResult(Set<Item> searchResults) {
		searchPanel =new SearchResultPanel(searchResults) ;
		this.setContentPane(searchPanel);
		revalidate();
	}
	
	protected void changeHomePage() {
		if(homepagePanel == null) {
			homepagePanel = new HomePage(user);
		}
		this.setContentPane(homepagePanel);
		revalidate();
	}

	public void changeRegisterPage() {
		// TODO Auto-generated method stub
		this.setContentPane(new RegisterPanel());
		revalidate();
	}

	protected void changeContentPanel(Item item) {
		if(contentPanel == null) {
			contentPanel = new ViewContentPanel();
		}
		item.accepts(contentPanel);
		revalidate();
	}
	
	protected void logOut() {
		this.loginPanel = new LoginPanel();
		this.setContentPane(loginPanel);
		this.homepagePanel = null;
		this.searchPanel = null;
		this.contentPanel = null;
		this.user = null;
		revalidate();
	}
}
