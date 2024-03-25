package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.Manager;

public class UserManagerPanel extends JPanel{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Manager manager;
	
	UserManagerPanel(Manager manager){
		//validate that its the manager

		this.manager = manager;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//create a home button
		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainUI.getInstance().changeHomePage();
			}
			
		});
		add(homeButton);
		
		//content list (make it a panel)
		createRequestList();
		
		revalidate();
		
		//add all requests 
	}
	
	private void createRequestList() {
		//gets list of users
	 	List<String[]> pending_users = UserDataBase.getInstance().getPending_users();
        JPanel listContainer = new JPanel();
        JScrollPane scrollPane = new JScrollPane(listContainer);
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.PAGE_AXIS));
        
        
        for (String[] pending_user : pending_users) {
        	listContainer.add(getRequestsDisplayItem(pending_user));
        }
        
       ;
        Dimension listContainerPreferredSize = listContainer.getPreferredSize();
        int preferredWidth = listContainerPreferredSize.width;
        int preferredHeight = listContainerPreferredSize.height;

        // Set the preferred size of the list container to ensure it fits within the scroll pane
        listContainer.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        listContainer.revalidate();
        add(scrollPane);
	}
	
	private JPanel getRequestsDisplayItem(String[] pending_user) {
		//name
		//id
		//publisher
		//request type
		//textfield for inputting the content
		//adding button
		//removing button
		JPanel requestDisplay = new JPanel();
		requestDisplay.setLayout(new GridLayout(7,1));
		JLabel name = new JLabel("Name: " + pending_user[0]);
		JLabel email = new JLabel("Email" + pending_user[2]);
		JLabel type = new JLabel("Type: " +  pending_user[3]);

        JPanel buttonPanel = new JPanel();
        JButton addRequest = new JButton("Approve");
        addRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Approved");
				UserDataBase.getInstance().approveUser(pending_user[0], manager);;
			}
        	
        });
        
        JButton rejectRequest = new JButton("Reject");
        rejectRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Not Proved");
				UserDataBase.getInstance().rejectUser(pending_user[0], manager);;
			}
        	
        });
        
        buttonPanel.add(addRequest);
        buttonPanel.add(rejectRequest);
        
        requestDisplay.add(name);
        requestDisplay.add(email);
        requestDisplay.add(type);
        requestDisplay.add(buttonPanel);
        
        requestDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        requestDisplay.setSize(300, 50);
        requestDisplay.revalidate();
		return requestDisplay;
	}
}
