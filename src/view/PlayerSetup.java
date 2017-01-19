package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PlayerSetupController;

public class PlayerSetup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JComboBox<Integer> pointsField;
	
	/**
	 * Create the frame.
	 */
	public PlayerSetup() {
		setTitle("Player Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlayerName = new JLabel("Player name:");
		lblPlayerName.setBounds(15, 16, 93, 20);
		contentPane.add(lblPlayerName);
		
		nameField = new JTextField();
		nameField.setBounds(128, 13, 285, 26);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblPlayerPoints = new JLabel("Player points:");
		lblPlayerPoints.setBounds(15, 52, 97, 20);
		contentPane.add(lblPlayerPoints);
		
		pointsField = new JComboBox<Integer>();
		pointsField.setBounds(128, 49, 285, 26);
		pointsField.setEditable(true);
		for (int i = 25; i <= 1000; i = i + 25)
		{
			pointsField.addItem(i);
		}
		contentPane.add(pointsField);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new PlayerSetupController(this));
		btnOk.setBounds(298, 91, 115, 29);
		contentPane.add(btnOk);
		
		this.setVisible(true);
	}
	
	public String getName() {
		return this.nameField.getText().toString();
	}

	public String getPoints() {
		return this.pointsField.getSelectedItem().toString();
	}
}
