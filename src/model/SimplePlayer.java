package model;

import java.io.Serializable;

import model.interfaces.Player;

@SuppressWarnings("serial")
public class SimplePlayer implements Player, Serializable {
	private String playerId, playerName;
	private int points, bet, result;

	public SimplePlayer(String playerId, String playerName, int points) {
		if(playerId.length() == 0)
			throw new IllegalArgumentException("Invalid player id");
		else if (playerName.length() == 0)
			throw new IllegalArgumentException("Player name must be at least 1 character");
		else if (points < 0)
			throw new IllegalArgumentException("Points have to be larger than 0");

		this.playerId = playerId;
		this.playerName = playerName;
		this.points = points;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public boolean placeBet(int bet) {
		try {
			// Place bet only if player has enough points
			int points = getPoints();
			
			if (bet > points)
			{
				throw new Exception("Insufficient points");
			}
			else
			{	
				this.bet = bet;
				
				return true;
			}
		} catch (Exception e)
		{
			return false;
		}
		
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public int getResult() {
		return this.result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		
		result.append("Player " + this.playerName + "(id: " + this.playerId + ") ");
		result.append("has " + this.points + " points\n");
		
		return result.toString();
	}
}
