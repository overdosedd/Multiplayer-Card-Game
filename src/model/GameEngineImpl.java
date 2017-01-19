package model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

@SuppressWarnings("serial")
public class GameEngineImpl implements GameEngine, Serializable {
	// List of players
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<GameEngineCallback> gameEngineCallbackList = new ArrayList<GameEngineCallback>(); // List of callbacks
	private ArrayList<GameEngineCallback> gameEngineCallbackListConsole = new ArrayList<GameEngineCallback>(); // List of callbacks
	private int houseResult;
	
	@Override
	public void dealPlayer(Player player, int delay) {
		if(player == null)
			throw new IllegalArgumentException("Player cannot be null");
		else if (delay <= 0)
			throw new IllegalArgumentException("Delay must be greater than 0");
		
		Player currentPlayer = getPlayer(player.getPlayerId());
		
		if (currentPlayer.getBet() > 0)
		{
			int playerBustTotal = 0;
			
			// Get a set of shuffled cards
			Deque<PlayingCard> shuffledDeck = getShuffledDeck();;
			PlayingCard cardDealt;
			
			// Continue looping until player busts (reaches 21)
			while (playerBustTotal <= BUST_LEVEL)
			{
				// Get the first card in the shuffled set of cards
				cardDealt = shuffledDeck.getFirst();
				
				if (playerBustTotal + cardDealt.getScore() <= BUST_LEVEL)
				{
					// Increment the total bust and player score
					playerBustTotal = playerBustTotal + cardDealt.getScore();				
				}
				else
				{
					// Display card that caused a bust
					for (GameEngineCallback currentCallback : gameEngineCallbackList)
					{
						if (playerList.indexOf(currentPlayer) == gameEngineCallbackList.indexOf(currentCallback))
						{
							currentCallback.bustCard(player, cardDealt, this);
						}
					}
					
					break;
				}
				
				// Sleep for x seconds
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Print the card
				for (GameEngineCallback currentCallback : gameEngineCallbackList)
				{
					if (playerList.indexOf(currentPlayer) == gameEngineCallbackList.indexOf(currentCallback))
					{
						currentCallback.nextCard(player, cardDealt, this);
					}
				}
				
				// Remove the card we just dealt
				shuffledDeck.removeFirst();			
			}
			
			// Update player result
			currentPlayer.setResult(playerBustTotal);
			
			for (GameEngineCallback currentCallback : gameEngineCallbackList)
			{
				if (playerList.indexOf(currentPlayer) == gameEngineCallbackList.indexOf(currentCallback))
				{
					currentCallback.result(currentPlayer, currentPlayer.getResult(), this);
				}
			}
			
		}		
	}

	@Override
	public void dealHouse(int delay) {
		int bustTotal = 0;
		
		if (delay <= 0)
			throw new IllegalArgumentException("Delay must be greater than 0");
		
		// Get a set of shuffled cards
		Deque<PlayingCard> shuffledDeck = getShuffledDeck();;
		PlayingCard cardDealt;
		
		// Continue looping until house busts (reaches 21)
		while (bustTotal <= BUST_LEVEL)
		{
			// Get the first card in the shuffled set of cards
			cardDealt = shuffledDeck.getFirst();
			
			if (bustTotal + cardDealt.getScore() <= BUST_LEVEL)
			{
				// Increment the total bust and player score
				bustTotal = bustTotal + cardDealt.getScore();				
			}
			else
			{
				// Display card that caused a bust
				for (GameEngineCallback currentCallback : gameEngineCallbackList)
				{
					for (Player currentPlayer : playerList)
					{
						if (gameEngineCallbackList.indexOf(currentCallback) == playerList.indexOf(currentPlayer) && currentPlayer.getBet() > 0)
							currentCallback.houseBustCard(cardDealt, this);
					}
				}
				
				break;
			}
			
			// Sleep for x seconds
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Print the card
			for (GameEngineCallback currentCallback : gameEngineCallbackList)
			{
				for (Player currentPlayer : playerList)
				{
					if (gameEngineCallbackList.indexOf(currentCallback) == playerList.indexOf(currentPlayer) && currentPlayer.getBet() > 0)
						currentCallback.nextHouseCard(cardDealt, this);
				}
			}
			
			// Remove the card we just dealt
			shuffledDeck.removeFirst();			
		}
		
		// Update house result
		houseResult = bustTotal;
		for (GameEngineCallback currentCallback : gameEngineCallbackList)
		{
			for (Player currentPlayer : playerList)
			{
				if (gameEngineCallbackList.indexOf(currentCallback) == playerList.indexOf(currentPlayer) && currentPlayer.getBet() > 0)
					currentCallback.houseResult(houseResult, this);
			}
		}
		
		// Calculate result
		this.calculateResult();
	
	}

	@Override
	public void addPlayer(Player player) {
		// Make sure player id's are unique
		if (playerList.contains(getPlayer(player.getPlayerId())))
			throw new IllegalArgumentException("Player by id " + player.getPlayerId() + " already exists");

		playerList.add(player);		
	
	}

	@Override
	public Player getPlayer(String id) {
		for (Player p : playerList)
		{
			if (p.getPlayerId().equals(id))
			{
				return p;
			}
		}
		return null;
	
	}

	@Override
	public boolean removePlayer(Player player) {
		/* First get the actual player in the array that stores our player */
		Player p = getPlayer(player.getPlayerId());
		
		int index = playerList.indexOf(p);
		gameEngineCallbackList.remove(index);
		gameEngineCallbackListConsole.remove(index);
		playerList.remove(p);
		
		return true;
	
	}
	
	private GameEngineCallback getCallback(Player p)
	{
		for (GameEngineCallback callback : gameEngineCallbackList) 
		{
			if (gameEngineCallbackList.indexOf(callback) == playerList.indexOf(getPlayer(p.getPlayerId())))
			{
				return callback;
			}
		}
	
		return null;
	}
	@Override
	public void calculateResult() {
		List<Player> drawList = new ArrayList<Player>();
		
		int highest = 0;
		String highestPlayer = null;
		// Find out which player has highest score
		for (Player p : playerList)
		{
			// Update winner if player has higher score than previous highest player
			if (p.getResult() >= highest)
			{
				highest = p.getResult();
				highestPlayer = p.getPlayerId();
			}
		}
		
		// Create a dummy winner object
		Player winner = new SimplePlayer(highestPlayer, getPlayer(highestPlayer).getPlayerName(), getPlayer(highestPlayer).getPoints());
		winner.placeBet(getPlayer(highestPlayer).getBet());
		winner.setResult(getPlayer(highestPlayer).getResult());

		// Find out if there is a draw between player with highest score and the house
		if (winner.getResult() == houseResult)
		{
			getCallback(winner).finalResult("draw", null, -1, null, this);;
			// Reset player bet and result
			getPlayer(winner.getPlayerId()).placeBet(0);
		}
		// If the house has a higher result than the highest human player
		else if(houseResult > winner.getResult())
		{
			for (Player currentPlayer : playerList)
			{
				if (currentPlayer.getBet() > 0)
					getCallback(currentPlayer).finalResult("house win", null, houseResult, null, this);
			}
			
			// Update result and notify players how much they lost
			for (Player currentPlayer : playerList)
			{
				if (currentPlayer.getBet() > 0)
				{
					// Update result
					currentPlayer.setPoints(currentPlayer.getPoints() - currentPlayer.getBet());
				
					getCallback(currentPlayer).finalResult("player lost points", currentPlayer, -1, null, this);
					
					// Reset player's bet
					currentPlayer.placeBet(0);
				}
			}
		}
		// If one or more players is the winner
		else if(winner.getResult() > houseResult)
		{
			// Find out if we have multiple players with the same highest score
			for (Player p : playerList)
			{
				if(p.getResult() == winner.getResult())
				{
					drawList.add(p);
				}
			}
		
			// We have a draw among human players
			if (drawList.size() > 1)
			{
				for (Player currentPlayer : playerList)
				{
					if (currentPlayer.getPlayerId().equals(winner.getPlayerId()) )
					{
						getCallback(currentPlayer).finalResult("player draw", null, winner.getResult(), drawList, this);
					}
				}
			}
			else if (drawList.size() == 1)
			{
				// One player wins the game
				for (Player currentPlayer : playerList)
				{
					if (currentPlayer.getPlayerId().equals(winner.getPlayerId()) )
					{
						getCallback(currentPlayer).finalResult("player win", winner, winner.getResult(), null, this);
					}
				}
			}
			
			// Update result and notify players how much they won/ lost
			for (Player currentPlayer : playerList)
			{
				if (currentPlayer.getBet() > 0)
				{
					if (currentPlayer.getResult() == winner.getResult())
					{
						currentPlayer.setPoints(currentPlayer.getPoints() + currentPlayer.getBet());
						
						getCallback(currentPlayer).finalResult("player won points", currentPlayer, -1, null, this);	
					}
					else
					{
						currentPlayer.setPoints(currentPlayer.getPoints() - currentPlayer.getBet());

						getCallback(currentPlayer).finalResult("player lost points", currentPlayer, -1, null, this);							
					}	
				}
				
				// Reset player's bet
				currentPlayer.placeBet(0);
			}
		}
		
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbackList.add(gameEngineCallback);
		gameEngineCallbackListConsole.add(gameEngineCallback);
	
	}
	
	@Override
	public Collection<Player> getAllPlayers() {
		return playerList;
	
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		player.placeBet(bet);
		
		return true;
	
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		// Create an empty array
		List<PlayingCard> list = new ArrayList<PlayingCard>();
		
		// Add the standard 52 card deck
		for(Suit suit : Suit.values())
		{
			for(Value value : Value.values())
			{
				list.add(new PlayingCardImpl(suit, value));
			}
		}
		
		// Shuffle the array
		Collections.shuffle(list);
		// Copy the array to a deque array
		Deque<PlayingCard> playerCards = new ArrayDeque<PlayingCard>(list);
		
		return playerCards;
	
	}
}
