package callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class NextHouseCallback extends Callback {

	private PlayingCard card;
	@SuppressWarnings("unused")
	private GameEngine engine;
	
	public NextHouseCallback (PlayingCard card, GameEngine engine)
	{
		this.card = card;
		this.engine = engine;
	}

	@Override
	public void execute(GameEngineCallback callback) {
		callback.nextHouseCard(card, null);
	}
	
	public String buildString() 
	{
		return "House has got a " + card.getSuit() + " " + card.getValue() + " which is worth " + card.getScore();
	}
}
