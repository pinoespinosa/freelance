package view;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import classes.Articulo;
import classes.ItemVenta;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.List;
import javax.swing.ListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class MainViewTienda {

	private JFrame frame;

	/**
	 * Create the application.
	 * @param parent 
	 */
	public MainViewTienda(JFrame parent) {
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
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblArticulo = new JLabel("Articulo");
		GridBagConstraints gbc_lblArticulo = new GridBagConstraints();
		gbc_lblArticulo.gridheight = 2;
		gbc_lblArticulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblArticulo.gridx = 1;
		gbc_lblArticulo.gridy = 1;
		frame.getContentPane().add(lblArticulo, gbc_lblArticulo);
		
		
	
		
		JLabel lblProductosEnEl = new JLabel("Productos en el carro");
		GridBagConstraints gbc_lblProductosEnEl = new GridBagConstraints();
		gbc_lblProductosEnEl.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductosEnEl.gridx = 4;
		gbc_lblProductosEnEl.gridy = 1;
		frame.getContentPane().add(lblProductosEnEl, gbc_lblProductosEnEl);
		
		JList<Articulo> list = new JList<Articulo>(new DefaultListModel<Articulo>());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 2;
		gbc_list.gridy = 2;
		frame.getContentPane().add(list, gbc_list);
		
		JList<ItemVenta> list_1 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridheight = 3;
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 4;
		gbc_list_1.gridy = 2;
		frame.getContentPane().add(list_1, gbc_list_1);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		GridBagConstraints gbc_lblCantidad = new GridBagConstraints();
		gbc_lblCantidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidad.anchor = GridBagConstraints.EAST;
		gbc_lblCantidad.gridx = 1;
		gbc_lblCantidad.gridy = 3;
		frame.getContentPane().add(lblCantidad, gbc_lblCantidad);
		
		JButton btnAgregarAlCarro = new JButton("Agregar al carro");
	
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 3;
		
		frame.getContentPane().add(spinner, gbc_spinner);
		GridBagConstraints gbc_btnAgregarAlCarro = new GridBagConstraints();
		gbc_btnAgregarAlCarro.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAgregarAlCarro.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarAlCarro.gridx = 2;
		gbc_btnAgregarAlCarro.gridy = 4;
		frame.getContentPane().add(btnAgregarAlCarro, gbc_btnAgregarAlCarro);
		
		JButton btnNewButton = new JButton("Vender");

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 5;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		
		btnAgregarAlCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cant = Integer.parseInt(spinner.getValue().toString());
				if (cant > 0 && list.getSelectedValue() != null)
					((DefaultListModel<ItemVenta>) list_1.getModel())
							.addElement(new ItemVenta(list.getSelectedValue(), cant));
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitView.tienda.get
			}
		});
		
		for (Articulo art : InitView.tienda.getArticulos()) {
			((DefaultListModel)list.getModel()).addElement(art);
		}
		
		
		
	}

}
