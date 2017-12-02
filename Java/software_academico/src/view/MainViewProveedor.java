package view;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainViewProveedor {

	private JFrame frame;


	/**
	 * Create the application.
	 * @param parent 
	 */
	public MainViewProveedor(JFrame parent) {
		initialize();
		frame.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnProveerArticulos = new JButton("Proveer Articulos");
		GridBagConstraints gbc_btnProveerArticulos = new GridBagConstraints();
		gbc_btnProveerArticulos.insets = new Insets(0, 0, 5, 5);
		gbc_btnProveerArticulos.gridx = 1;
		gbc_btnProveerArticulos.gridy = 1;
		frame.getContentPane().add(btnProveerArticulos, gbc_btnProveerArticulos);
	}

}
