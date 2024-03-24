package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.UserType;

public class RegisterPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected RegisterPanel() {
		 setLayout(new GridLayout(5, 2));
		 
	        
	        JLabel emailLabel = new JLabel("Email");
	        add(emailLabel);
	        
	        JTextField emailField = new JTextField();
	        add(emailField);
	  
	        JLabel usernameLabel = new JLabel("Username:");
	        add(usernameLabel);

	        JTextField usernameField = new JTextField();
	        add(usernameField);

	        JLabel passwordLabel = new JLabel("Password:");
	        add(passwordLabel);
	        
	        JPasswordField passwordField = new JPasswordField();
	        add(passwordField);
	        
	        JList<String> optionsPanel = new JList<>();
	        Vector<String> options = new Vector<String>();
	        optionsPanel.setVisible(false);
	        
	        for(UserType userType: UserType.values()) {
	        	options.add(userType.toString());
	        }
	        JComboBox<String> optionsList = new JComboBox<String>(options);
	        this.add(optionsList);

	        JButton loginButton = new JButton("Register");
	        loginButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Handle login button click event
	            	String email = emailField.getText();
	            	String username = usernameField.getText();
	                String password = new String(passwordField.getPassword());
	                String type = optionsList.getSelectedItem().toString();

	                // You can perform login authentication here
	                // For simplicity, let's just display a message
	               try {
	            	   UserDataBase.getInstance().registerNewUser(username, password, email, UserType.valueOf(type));
	            	   MainUI.getInstance().logOut();
	            	   
	               }catch(Exception exception) {
	            	   
	               }
	              
	            }
	        });
	        add(loginButton);
	}
}
