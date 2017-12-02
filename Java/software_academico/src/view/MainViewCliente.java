package view;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainViewCliente {

	private JFrame frame;
	private JFrame parent;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	private JTextField textField;

	/**
	 * Create the application.
	 * @param parent 
	 */
	public MainViewCliente(JFrame parent) {
		initialize();
		this.frame.setVisible(true);
		this.parent = parent;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnComprarArticulo = new JButton("Comprar Articulo(Poner en el carrito)");
		btnComprarArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				parent.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.anchor = GridBagConstraints.EAST;
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 3;
		gbc_btnVolver.gridy = 0;
		frame.getContentPane().add(btnVolver, gbc_btnVolver);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		GridBagConstraints gbc_btnCrearArticulo = new GridBagConstraints();
		gbc_btnCrearArticulo.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearArticulo.gridx = 3;
		gbc_btnCrearArticulo.gridy = 2;
		frame.getContentPane().add(btnComprarArticulo, gbc_btnCrearArticulo);
		
		JButton btnPagarArticulo = new JButton("Pagar Articulo");
		GridBagConstraints gbc_btnPagarArticulo = new GridBagConstraints();
		gbc_btnPagarArticulo.insets = new Insets(0, 0, 5, 5);
		gbc_btnPagarArticulo.gridx = 3;
		gbc_btnPagarArticulo.gridy = 3;
		frame.getContentPane().add(btnPagarArticulo, gbc_btnPagarArticulo);
	}

}
