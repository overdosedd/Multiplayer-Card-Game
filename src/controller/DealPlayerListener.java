 package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import model.GameEngineClientStub;
import model.interfaces.Player;
import view.ClientGUI;

public class DealPlayerListener implements ActionListener {
	private ClientGUI frame;
	private static Logger logger = Logger.getLogger("assignment2");

	public DealPlayerListener(ClientGUI frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Player localPlayer = frame.getLocalPlayer();
		
		if (localPlayer.getBet() > 0)
		{
			
//				gameEngine.addGameEngineCallback(new GameEngineCallbackImpl(frame));
//				gameEngine.addPlayer(player);
//				gameEngine.placeBet(player, player.getBet());
//				gameEngine.dealPlayer(player, 10);
//				gameEngine.dealHouse(10);
//				
//				// Reset playet bet to 0 and reset status bar
//				gameEngine.getPlayer(player.getPlayerId()).placeBet(0);
//				frame.setStatus(player.getPoints(), player.getBet());
//				gameEngine.removePlayer(player);
				GameEngineClientStub clientStub = frame.getClientStub();

				for (Player currentPlayer : clientStub.getAllPlayers())
				{
					clientStub.dealPlayer(currentPlayer, 10);
				}

				clientStub.dealHouse(10);
				
				// Get updated player object from server
				Player playerFromServer = clientStub.getPlayer(localPlayer.getPlayerId());
				// Reset player bet and update player balance in status bar
				localPlayer.setPoints(playerFromServer.getPoints());
				localPlayer.placeBet(0);
				
				// Update status bar with new points and bet
				frame.setStatus(localPlayer.getPoints(), localPlayer.getBet());
				
				// Get all players from server and log player info to console
				List<Player> allPlayers = (List<Player>) clientStub.getAllPlayers();
				for (Player p : allPlayers)
				{
					logger.log(Level.INFO, p.toString());
				}
		}
		else
		{
			JOptionPane.showMessageDialog(null,
		    "You haven't placed a bet",
		    "Error",
		    JOptionPane.ERROR_MESSAGE);
		}
	}
}
