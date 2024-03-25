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

import group7891234.deliverable2.library.LibraryDataBase;
import group7891234.deliverable2.library.item.Item;
import group7891234.deliverable2.users.UserDataBase;
import group7891234.deliverable2.users.factory.Manager;

public class ItemManagerPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Manager manager;
	
	public ItemManagerPanel(Manager user) {
		this.manager = user;
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
		
		createRequestList();

	}
	private void createRequestList() {
		//gets list of users
	 	Set<Item> items = LibraryDataBase.getInstance().getItems();
        JPanel listContainer = new JPanel();
        JScrollPane scrollPane = new JScrollPane(listContainer);
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.PAGE_AXIS));
        
        
        for (Item item: items) {
        	listContainer.add(getItemDisplayItem(item));
        }
        Dimension listContainerPreferredSize = listContainer.getPreferredSize();
        int preferredWidth = listContainerPreferredSize.width;
        int preferredHeight = listContainerPreferredSize.height;

        // Set the preferred size of the list container to ensure it fits within the scroll pane
        listContainer.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        listContainer.revalidate();
        add(scrollPane);
	}
	
	private JPanel getItemDisplayItem(Item item) {
		//name
		//id
		//publisher
		//request type
		//textfield for inputting the content
		//adding button
		//removing button
		JPanel requestDisplay = new JPanel();
		requestDisplay.setLayout(new GridLayout(7,1));
		JLabel name = new JLabel("Name: " + item.getName());
		JLabel id = new JLabel("id: " + item.getId());
		JLabel type = new JLabel("Type: " + item.getType().toString());

        JPanel buttonPanel = new JPanel();
        String enableText = "";
        if(item.isEnabled()){
        	enableText = "Disable";
        } else {
        	enableText = "Enable";
        }
        JButton enable = new JButton(enableText);
        enable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(item.isEnabled()) {
					item.disable();
					enable.setText("Enable");
				}else {
					item.enable();
					enable.setText("Disable");
				}
				enable.revalidate();
			}
        	
        });
        
        buttonPanel.add(enable);
        
        requestDisplay.add(name);
        requestDisplay.add(id);
        requestDisplay.add(type);
        requestDisplay.add(buttonPanel);
        
        requestDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        requestDisplay.setSize(300, 50);
        requestDisplay.revalidate();
		return requestDisplay;
	}


}
