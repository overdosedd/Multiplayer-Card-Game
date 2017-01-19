package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.interfaces.Player;
import view.ClientGUI;

public class PlaceBetListener implements ActionListener {
	private ClientGUI frame;
	
	public PlaceBetListener(ClientGUI frame)
	{
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String playerBet_s, points_s;
		int playerBet = 0, points;
		
		playerBet_s = JOptionPane.showInputDialog(frame, "Enter player bet:");
		while (true)
		{
				// Lets user cancel placing a bet
				if (playerBet_s == null)
					break;
				
				// Check if bet is an integer
				if (!isInteger(playerBet_s))
				{
					JOptionPane.showMessageDialog(null,
				    "Input is empty or is invalid",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
					
					playerBet_s = JOptionPane.showInputDialog(frame, "Enter player bet:");
				}
				else
				{
					// Bet input validated
					playerBet = Integer.parseInt(playerBet_s);
					
					break;
				}
		}
		
		// Check if player has more points than bet
		while (true)
		{
			// Make sure player has more points than bet
			if (frame.getPoints() >= playerBet)
			{
					// Update status bar
					frame.setStatus(frame.getClientStub().getPlayer(frame.getLocalPlayer().getPlayerId()).getPoints() - playerBet, playerBet);
					
					if (playerBet != 0)
						frame.setTempText("Player " + frame.getLocalPlayer().getPlayerName() + " has placed a bet of " + playerBet + "\n\n");
					
					break;
			}
			else
			{
				// Insufficient points, ask if player wants to update his points
				playerBet = 0;
				
				int n = JOptionPane.showConfirmDialog(
					    frame,
					    "Insufficient points. Would you like to update your points?",
					    "Error",
					    JOptionPane.YES_NO_OPTION);
				
				if (n == JOptionPane.YES_OPTION)
					points_s = JOptionPane.showInputDialog(frame, "Enter player points:");
				else
					break;
				
				while (true)
				{
					// Lets player cancel updating points
					if (n == JOptionPane.YES_OPTION)
					{
						// Validate input
						if (!isInteger(points_s))
						{
							JOptionPane.showMessageDialog(null,
						    "Input is empty or is invalid",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
							
							points_s = JOptionPane.showInputDialog(frame, "Enter player points:");
						}
						else
						{
							// Successful validation
							points = Integer.parseInt(points_s);
							
							// Update points for both local player and player at server side
							frame.getLocalPlayer().setPoints(points);
							
							Player playerFromServer = frame.getClientStub().getPlayer(frame.getLocalPlayer().getPlayerId());
							playerFromServer.setPoints(points);

							Player playerFromServer2 = frame.getClientStub().getPlayer(frame.getLocalPlayer().getPlayerId());
							
							frame.setStatus(playerFromServer2.getPoints(), playerFromServer2.getBet());
							
							break;
						}
					}
					else if (n == JOptionPane.NO_OPTION)
					{
						// Cancel update points
						break;
					}
				}
				
				break;
			}
		}
		
		frame.getClientStub().placeBet(frame.getLocalPlayer(), playerBet);
		frame.getLocalPlayer().placeBet(playerBet);
	}
	
	public static boolean isInteger(String s)
	{
		try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
		
		return true;
	}
}
