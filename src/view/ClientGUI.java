package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.GameEngineClientStub;
import model.SimplePlayer;
import model.interfaces.Player;

public class ClientGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameEngineClientStub gameEngineClientStub;
	// newPlayer passed in from player setup
	private Player localPlayer;
		
	private MenuBar menuBar = new MenuBar();
	private ToolBar toolBar = new ToolBar(this);
	private EditorPanel editorPanel = new EditorPanel(this);
	private StatusBar statusBar = new StatusBar();

	public static void main(String[] args) {
		new PlayerSetup();
	}
	
	/**
	 * Create the frame.
	 */
	public ClientGUI(Player playerFromSetup)
	{
		setTitle("Deal Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBounds(100, 100, 640, 480);

		// Connect to the server, exit if server is offline
		gameEngineClientStub = new GameEngineClientStub(this);
		// Add the local player to the server
		try {
			// Generate random player id
			int playerId = (int )(Math.random() * 100 + 1);
			
			Player player = new SimplePlayer(Integer.toString(playerId), playerFromSetup.getPlayerName(), playerFromSetup.getPoints());
			gameEngineClientStub.addPlayer(player);

			// Store the player locally
			this.localPlayer = player;
		} catch (Exception e) {
			System.exit(0);
		}
		
		add(toolBar, BorderLayout.NORTH);
		add(editorPanel, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		setStatus(playerFromSetup.getPoints(), 0);
		
		setJMenuBar(menuBar);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	public void setTempText(String text) {
		editorPanel.setTempText(text);		
	}

	public void appendText(String text) {
		editorPanel.appentText(text);
	}
	
	public void setStatus(int points, int bet)
	{
		int total = points + bet;
		
		statusBar.setStatus("Player name: " + localPlayer.getPlayerName() + ", " + points + " available points, " + bet + " points put on bet" + " (total points: " + total + ")");
	}

	public Player getLocalPlayer() {
		return this.localPlayer;
	}

	public int getPoints() {
		return this.localPlayer.getPoints();
	}
	
	public GameEngineClientStub getClientStub () {
		return this.gameEngineClientStub;
	}
	
	public int getEditorWidth() {
		int width = editorPanel.getWidth();
		return width;
	}
}
