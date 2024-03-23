package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import group7891234.deliverable2.users.User;

public class MainUI extends JFrame{

	/**
	 * 
	 */
	private static MainUI instance;
	protected static User user = null;
	private static final long serialVersionUID = 1L;
	private JPanel loginPanel;
	private JPanel homepagePanel;
	
	public static void main(String[] args) {
		JFrame frame = MainUI.getInstance();
		
		
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private MainUI() {
		loginPanel = new LoginPanel();
		homepagePanel = new HomePage();
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
	
	protected void changeHomePage() {
		this.setContentPane(homepagePanel);
		revalidate();
	}

	public void changeRegisterPage() {
		// TODO Auto-generated method stub
		this.setContentPane(new RegisterPanel());
		revalidate();
	}

}
