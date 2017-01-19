package callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class NextCardCallback extends Callback {
	private Player player;
	private PlayingCard card;
	@SuppressWarnings("unused")
	private GameEngine engine;
	
	public NextCardCallback (Player player, PlayingCard card, GameEngine engine)
	{
		this.player = player;
		this.card = card;
		this.engine = engine;
	}
	
	public void execute(GameEngineCallback callback)
	{
		callback.nextCard(player, card, null);
	}
	
	public String buildString() 
	{
//		StringBuilder string = new StringBuilder();
//		string.append("Player ");
//		string.append(player.getPlayerName());
//		string.append(" has got a ");
//		string.append(card.getSuit());
//		string.append(" ");
//		string.append(card.getValue());
//		string.append(" which is worth ");
//		string.append(card.getScore());

		return "Player " + player.getPlayerName() + " has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore();
	}
}
