package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem exitItem = new JMenuItem("Exit");

	public MenuBar()
	{
		add(fileMenu);

		fileMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}
}
