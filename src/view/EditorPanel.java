package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea();

	public EditorPanel(ClientGUI view)
	{
		setLayout(new BorderLayout());

		add(textArea, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(textArea);
		this.add(scroll);
		
	}

	public void setTempText(String text)
	{
		textArea.setText(text);
	}
	
	public void appentText(String text)
	{
		textArea.append(text + "\n");
	}
}
