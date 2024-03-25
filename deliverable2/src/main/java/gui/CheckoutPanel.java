package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import group7891234.deliverable2.library.item.Item;

public class CheckoutPanel extends JPanel{
	
	CheckoutPanel(Item item){
		setLayout(new GridLayout(3, 0));
		//home button
		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainUI.getInstance().changeHomePage();
			}
			
		});
		add(homeButton);
		
		JPanel billingPanel = new JPanel(new GridLayout(3, 2));
        billingPanel.add(new JLabel("Name:"));
        billingPanel.add(new JTextField());
        billingPanel.add(new JLabel("Address:"));
        billingPanel.add(new JTextField());
        billingPanel.add(new JLabel("Email:"));
        billingPanel.add(new JTextField());
        add(billingPanel);

        JPanel paymentPanel = new JPanel();
        JRadioButton creditCardRadio = new JRadioButton("Credit Card");
        JRadioButton paypalRadio = new JRadioButton("PayPal");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(creditCardRadio);
        paymentGroup.add(paypalRadio);
        paymentPanel.add(new JLabel("Card Info"));
        paymentPanel.add(new JTextField(""));
        paymentPanel.add(creditCardRadio);
        paymentPanel.add(paypalRadio);
        add(paymentPanel);
		
		JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for checkout
                JOptionPane.showMessageDialog(null, "Item Bought YIPEE");
                MainUI.getInstance().changeHomePage();
            }
        });
        add(checkoutButton);
	}
}
