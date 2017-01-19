package model;

import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.ClientGUI;

public class GameEngineCallbackConsole implements GameEngineCallback {
	private String lineSeparator;
	protected static final int lineLength = 200;
	
	public GameEngineCallbackConsole (ClientGUI frame) {
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
		System.out.println("Player " + player.getPlayerName() + " has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore()); 		
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		System.out.println("Player " + player.getPlayerName() + " busted with a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore());
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		System.out.println();
		System.out.println("Player " + player.getPlayerName() + " has got a score of " + result); 
		
		System.out.println(lineSeparator);
		System.out.println();
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		System.out.println("House has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore()); 
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		System.out.println("The house busted with a " + card.getSuit() + " " + card.getValue() + " which has a score of " + card.getScore());
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		System.out.println();
		System.out.println("The house has got a score of " + result);
		
		System.out.println(lineSeparator);
		System.out.println();
	}

	@Override
	public void finalResult(String result, Player player, int score, List<Player> drawList, GameEngine engine) {
		if (result.equals("draw")) {
			System.out.println("The game is a draw");
		}
		else if (result.equals("house win"))
		{
			System.out.println("The house has won with a score of " + score);
		}
		else if (result.equals("player draw"))
		{
			System.out.println("The game is tied between the following users:");

			for (Player p : drawList)
			{
				System.out.println(p.getPlayerName() + " has won the game with a score of " + score);
			}
		}
		else if (result.equals("player win"))
		{
			System.out.println(player.getPlayerName() + " has won the game with a score of " + score);
		}
		else if (result.equals("player won points"))
		{
			System.out.println("Player " + player.getPlayerName() + " has won " + player.getBet() + " points");
		}
		else if (result.equals("player lost points"))
		{
			System.out.println(player.getPlayerName() + " has lost " + player.getBet() + " points");
		}
		
		System.out.println(lineSeparator);
		System.out.println();
	}
	
}
