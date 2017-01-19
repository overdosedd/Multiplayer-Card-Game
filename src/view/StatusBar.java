package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.Player;

public class StatusBar extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Player player;
	private JLabel status = new JLabel("");
	
	public StatusBar()
	{
		setLayout(new GridLayout(1, 3));
		add(status);
	}

	public StatusBar(Player newPlayer)
	{
		this.player = newPlayer;
	}

	public void setStatus(String newStatus)
	{
		status.setText(newStatus);
	}
}
