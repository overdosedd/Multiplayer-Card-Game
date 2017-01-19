package view;

import javax.swing.JButton;
import javax.swing.JToolBar;
import controller.DealPlayerListener;
import controller.PlaceBetListener;

public class ToolBar extends JToolBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton dealPlayer = new JButton("Go!");
	private JButton placeBet = new JButton("Place bet");

	public ToolBar(ClientGUI frame)
	{
		setFloatable(false);
		
		dealPlayer.addActionListener(new DealPlayerListener(frame));
		placeBet.addActionListener(new PlaceBetListener(frame));
		
		// JToolBar has a BoxLayout by default
		add(dealPlayer);
		add(placeBet);
	}
}
