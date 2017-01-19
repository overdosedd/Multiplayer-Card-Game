package callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class ResultCallback extends Callback {

	private Player player;
	private int result;
	@SuppressWarnings("unused")
	private GameEngine engine;
	
	public ResultCallback (Player player, int result, GameEngine engine)
	{
		this.player = player;
		this.result = result;
		this.engine = engine;
	}

	@Override
	public void execute(GameEngineCallback callback) {
		callback.result(player, result, null);
	}
	
	public String buildString() 
	{
//		StringBuilder string = new StringBuilder();
//		string.append("Player ");
//		string.append(player.getPlayerName());
//		string.append(" has got a score of ");
//		string.append(result);

		return "Player " + player.getPlayerName() + " has got a score of " + result;
	}
}
