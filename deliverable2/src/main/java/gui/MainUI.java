package gui;

import javax.swing.JFrame;

public class MainUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {

		JFrame frame = MainUI.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true);
	}

	private static JFrame getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
