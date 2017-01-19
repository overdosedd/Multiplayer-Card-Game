package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.SimplePlayer;
import model.interfaces.Player;
import view.ClientGUI;
import view.PlayerSetup;

public class PlayerSetupController implements ActionListener {
	private PlayerSetup frame;
	private Player localPlayer;
	
	public PlayerSetupController(PlayerSetup frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Check if a name was given
		if (frame.getName().isEmpty())
			JOptionPane.showMessageDialog(frame, "Please enter player name");
		else
		{
			// Create a new player object
			localPlayer = new SimplePlayer(frame.getName(), frame.getName(), Integer.parseInt(frame.getPoints()));
			
			// Run main GUI
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
		        public void run() {
			  		new ClientGUI(localPlayer);
		        }
		    });
			
			// Close player setup
			frame.dispose();	
		}
	}
}
    	