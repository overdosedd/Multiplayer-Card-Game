package command;

import java.util.Collection;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class GetAllPlayersCommand extends Command {
	public GetAllPlayersCommand() {
		this.setType(GET);
	}
	
	public Collection<Player> retrieve(GameEngine engine) {
		return engine.getAllPlayers();
	}
}
