package callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public class HouseResultCallback extends Callback {

	private int result;
	@SuppressWarnings("unused")
	private GameEngine engine;
	
	public HouseResultCallback (int result, GameEngine engine)
	{
		this.result = result;
		this.engine = engine;
	}

	@Override
	public void execute(GameEngineCallback callback) {
		callback.houseResult(result, null);
		
	}
	
	public String buildString() 
	{
		return "The house has got a score of " + result;
	}
}
