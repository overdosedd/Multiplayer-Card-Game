package callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class HouseBustCallback extends Callback {
	private PlayingCard card;
	@SuppressWarnings("unused")
	private GameEngine engine;

	public HouseBustCallback (PlayingCard card, GameEngine engine)
	{
		this.card = card;
		this.engine = engine;
	}

	@Override
	public void execute(GameEngineCallback callback) {
		callback.houseBustCard(card, null);
	}
	
	public String buildString() 
	{
		return "The house busted with a " + card.getSuit() + " " + card.getScore() + " which has a score of " + card.getScore() + "\n";
	}
}
