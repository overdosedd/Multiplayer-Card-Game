package callback;

import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class FinalResultCallback extends Callback {
	private String result;
	private Player player;
	private int score;
	private List<Player> drawList;
	@SuppressWarnings("unused")
	private GameEngine engine;
	
	public FinalResultCallback (String result, Player player, int score, List<Player> drawList, GameEngine engine)
	{
		this.result = result;
		this.player = player;
		this.score = score;
		this.drawList = drawList;
		this.engine = engine;
	}
	
	@Override
	public void execute(GameEngineCallback callback) {
		callback.finalResult(result, player, score, drawList, null);

	}
}
