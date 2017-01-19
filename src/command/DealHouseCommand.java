package command;

import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class DealHouseCommand extends Command {
	public DealHouseCommand()
	{
		this.setName("deal house");
		this.setType(SET);
	}
	
	public boolean execute(GameEngine engine) {		
		engine.dealHouse(10);
		
		return true;
	}
}
