package callback;

import java.io.Serializable;

import model.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public abstract class Callback implements Serializable {
	public abstract void execute(GameEngineCallback callback);
}