package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import group7891234.deliverable2.library.item.ItemType;
import group7891234.deliverable2.request.RequestDataBase;
import group7891234.deliverable2.request.RequestType;
import group7891234.deliverable2.users.User;
import group7891234.deliverable2.users.factory.UserType;

public class RequestPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RequestPanel(){
		setLayout(new GridLayout(7,2));
		//addRequest(ItemType itemType, String name, String id, String publisher,  RequestType type, User user)
		JLabel itemType = new JLabel("Item Type: ");
		this.add(itemType);
        Vector<String> options = new Vector<String>();
        
        for(ItemType type: ItemType.values()) {
        	options.add(type.toString());
        }
        JComboBox<String> optionsList = new JComboBox<String>(options);
        this.add(optionsList);
        
		JLabel name = new JLabel("Name: ");
		this.add(name);
		JTextField nameField = new JTextField();
		this.add(nameField);
		
		JLabel id = new JLabel("Id: ");
		this.add(id);
		JTextField idField = new JTextField();
		this.add(idField);
		
		JLabel publisher = new JLabel("Publisher: ");
		this.add(publisher);
		JTextField publisherField = new JTextField();
		this.add(publisherField);
		
		JLabel requestType = new JLabel("Request Type: ");
		this.add(requestType);
        Vector<String> requestOptions = new Vector<String>();
        
        for(RequestType type: RequestType.values()) {
        	requestOptions.add(type.toString());
        }
        JComboBox<String> roptionsList = new JComboBox<String>(requestOptions);
        this.add(roptionsList);
        
        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RequestDataBase.getinstance().addRequest(ItemType.valueOf(optionsList.getSelectedItem().toString()),
						nameField.getText(), 
						idField.getText(), 
						publisherField.getText(), 
						RequestType.valueOf(roptionsList.getSelectedItem().toString()), 
						MainUI.user);
				
				MainUI.getInstance().changeHomePage();
			}
        });
        this.add(button);
        this.revalidate();
	}
	
}
