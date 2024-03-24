package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.request.Request;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.factory.Manager;

public class RequestManagerPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Manager manager;
	
	RequestManagerPanel(Manager manager){
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
		//add all requests 
	}
	
	private void createRequestList() {
		 	Set<Request> requests = RequestDataBase.getinstance().getRequests().keySet();
	        JPanel listContainer = new JPanel();
	        JScrollPane scrollPane = new JScrollPane(listContainer);

	        
	        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.PAGE_AXIS));
	        
	        
	        for (Request request : requests) {
	        	listContainer.add(getRequestsDisplayItem(request));
	        }
	        
	        listContainer.revalidate();
	        Dimension listContainerPreferredSize = listContainer.getPreferredSize();
	        int preferredWidth = listContainerPreferredSize.width;
	        int preferredHeight = listContainerPreferredSize.height;

	        // Set the preferred size of the list container to ensure it fits within the scroll pane
	        listContainer.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

	        add(scrollPane);
	}
	
	private JPanel getRequestsDisplayItem(Request request) {
		//name
		//id
		//publisher
		//request type
		//textfield for inputting the content
		//adding button
		//removing button
		JPanel requestDisplay = new JPanel();
		requestDisplay.setLayout(new GridLayout(7,1));
		JLabel name = new JLabel("Name: " + request.getName());
		JLabel idLabel = new JLabel("ID: " + request.getId());
        JLabel publisherLabel = new JLabel("Publisher: " + request.getPublisher());
        JLabel requestType = new JLabel("Request Type: " + request.getType().toString());
        JTextField content = new JTextField();
        
        JPanel buttonPanel = new JPanel();
        JButton addRequest = new JButton("Approve");
        addRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RequestDataBase.getinstance().approveRequest(request, content.getText());
			}
        	
        });
        
        JButton rejectRequest = new JButton("Reject");
        addRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RequestDataBase.getinstance().rejectRequest(request);
			}
        	
        });
        
        buttonPanel.add(addRequest);
        buttonPanel.add(rejectRequest);
        
        requestDisplay.add(name);
        requestDisplay.add(idLabel);
        requestDisplay.add(publisherLabel);
        requestDisplay.add(requestType);
        requestDisplay.add(content);
        requestDisplay.add(buttonPanel);
        
        requestDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        requestDisplay.setSize(300, 50);
        requestDisplay.revalidate();
		return requestDisplay;
	}

}
