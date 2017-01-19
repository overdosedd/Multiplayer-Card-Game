package model;

import java.io.Serializable;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.ClientGUI;

@SuppressWarnings("serial")
public class GameEngineCallbackImpl implements GameEngineCallback, Serializable {
	private ClientGUI frame;
	private String lineSeparator;
	protected static final int lineLength = 200;
	
	public GameEngineCallbackImpl () {
		
	}
	public GameEngineCallbackImpl (ClientGUI frame) {
		this.frame = frame;
		
		// Create a line separator
		String lineSeparator = "";
		for (int i = 0; i < lineLength; i++)
		{
			lineSeparator += "-";
		}
		this.lineSeparator = lineSeparator;
	}
	
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		frame.appendText("Player " + player.getPlayerName() + " has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore());
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		frame.appendText("Player " + player.getPlayerName() + " busted with a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore());
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		frame.appendText("\nPlayer " + player.getPlayerName() + " has got a score of " + result);

		frame.appendText(lineSeparator + '\n');
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		frame.appendText("House has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore());

	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		frame.appendText("The house busted with a " + card.getSuit() + " " + card.getValue() + " which has a score of " + card.getScore());
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		frame.appendText("\nThe house has got a score of " + result);
		
		frame.appendText(lineSeparator + '\n');
	}

	public void finalResult(String result, Player player, int score, List<Player> drawList, GameEngine engine) {
		if (result.equals("draw")) {
			frame.appendText("The game is a draw");
		}
		else if (result.equals("house win"))
		{
			frame.appendText("The house has won with a score of " + score);
		}
		else if (result.equals("player draw"))
		{
			frame.appendText("The game is tied between the following users:");
			
			for (Player p : drawList)
			{
				frame.appendText("\n" + p.getPlayerName() + " has won the game with a score of " + score);
			}
		}
		else if (result.equals("player win"))
		{
			frame.appendText(player.getPlayerName() + " has won the game with a score of " + score);
		}
		else if (result.equals("player won points"))
		{
			frame.appendText("Player " + player.getPlayerName() + " has won " + player.getBet() + " points");
		}
		else if (result.equals("player lost points"))
		{
			frame.appendText("Player " + player.getPlayerName() + " has lost " + player.getBet() + " points");
		}
		
		frame.appendText(lineSeparator + '\n');
	}
}
