package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import classes.ItemVenta;
import classes.Venta;

public class MainViewCliente {

	private JFrame frame;
	private JFrame parent;

	private java.util.List<ItemVenta> compra = new ArrayList<>();
	private JTextField textField;

	Comparator<ItemVenta> comparatorPrecio = new Comparator<ItemVenta>() {

		@Override
		public int compare(ItemVenta o1, ItemVenta o2) {
			if (o1.getArticulo().getPrecio() == o2.getArticulo().getPrecio())
				return 0;
			else if (o1.getArticulo().getPrecio() > o2.getArticulo().getPrecio())
				return 1;
			else
				return -1;

		}
	};

	Comparator<ItemVenta> comparatorNombre = new Comparator<ItemVenta>() {

		@Override
		public int compare(ItemVenta o1, ItemVenta o2) {
			return o1.getArticulo().getNombre().compareTo(o2.getArticulo().getNombre());
		}
	};

	Comparator<ItemVenta> comparatorSelected = comparatorNombre;
	JList<Venta> list_ventas;

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public MainViewCliente(JFrame parent) {
		initialize();
		frame.setVisible(true);
		this.parent = parent;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				frame.setVisible(false);
				parent.setVisible(true);

			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 1;
		gbc_btnVolver.gridy = 0;
		frame.getContentPane().add(btnVolver, gbc_btnVolver);

		ButtonGroup bg = new ButtonGroup();

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Registrar pago de venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		list_ventas = new JList<Venta>(new DefaultListModel<Venta>());
		GridBagConstraints gbc_list_ventas = new GridBagConstraints();
		gbc_list_ventas.gridheight = 2;
		gbc_list_ventas.fill = GridBagConstraints.BOTH;
		gbc_list_ventas.insets = new Insets(0, 0, 5, 5);
		gbc_list_ventas.gridx = 1;
		gbc_list_ventas.gridy = 0;
		panel_2.add(list_ventas, gbc_list_ventas);

		JComboBox<String> comboFormaPago = new JComboBox<String>();
		comboFormaPago.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Efectivo", "Tarjeta de Credito", "Tarjeta de Debito" }));
		comboFormaPago.setToolTipText("Forma de pago");
		GridBagConstraints gbc_comboFormaPago = new GridBagConstraints();
		gbc_comboFormaPago.insets = new Insets(0, 0, 5, 0);
		gbc_comboFormaPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFormaPago.gridx = 3;
		gbc_comboFormaPago.gridy = 0;
		panel_2.add(comboFormaPago, gbc_comboFormaPago);

		textField = new JTextField();
		textField.setToolTipText("Detalle");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnRegistrarPago = new JButton("Registrar pago");
		GridBagConstraints gbc_btnRegistrarPago = new GridBagConstraints();
		gbc_btnRegistrarPago.gridx = 3;
		gbc_btnRegistrarPago.gridy = 2;
		panel_2.add(btnRegistrarPago, gbc_btnRegistrarPago);

		updateScreen();

		btnRegistrarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboFormaPago.getSelectedItem() != null && list_ventas.getSelectedValue() != null) {

					if ("Preparada".equals(list_ventas.getSelectedValue().getEstado())) {
						list_ventas.getSelectedValue().setEstado("Paga");
						list_ventas.getSelectedValue().setFormaPago(comboFormaPago.getSelectedItem().toString());
						JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
						updateScreen();
					} else
						JOptionPane.showMessageDialog(null, "Solo se pueden pagar facturas \"Preparadas\"...");
				}
			}
		});

	}

	private void updateScreen() {
		updateVenta(list_ventas);
		list_ventas.updateUI();
	}

	private void updateVenta(JList<Venta> list) {

		DefaultListModel<Venta> listVenta = (DefaultListModel<Venta>) list.getModel();
		listVenta.clear();
		for (Venta venta : InitView.getTienda().getVentas()) {
			listVenta.addElement(venta);
		}

	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public java.util.List<ItemVenta> getCompra() {
		return compra;
	}

	public void setCompra(java.util.List<ItemVenta> compra) {
		this.compra = compra;
	}

}
