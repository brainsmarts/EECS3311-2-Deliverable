package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import group7891234.deliverable2.users.UserDataBase;

public class LoginPanel extends JPanel{

	public LoginPanel() {
        setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login button click event
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // You can perform login authentication here
                // For simplicity, let's just display a message
                if(UserDataBase.getInstance().Login(username, password)) 
                	//change from login to homepage            
                	MainUI.user = UserDataBase.getInstance().getUser(username);
                	MainUI.getInstance().changeHomePage();
               
            }
        });
        add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		MainUI.getInstance().changeRegisterPage();
        	}
        });
        
        add(registerButton);
	}
}
