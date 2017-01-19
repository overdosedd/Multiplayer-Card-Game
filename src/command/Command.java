package command;

import java.io.Serializable;

import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class Command implements Serializable {
	protected static final String GET = "get";
	protected static final String SET = "set";
	
	private String cmdName;
	private String type;
	
	public String getCmdName() {
		return cmdName;
	}

	public void setName(String name) {
		this.cmdName = name;
	};	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	};
	
	public boolean execute(GameEngine engine) {
		return false;
	}

	public Object retrieve(GameEngine engine) {
		return null;
	}
}
