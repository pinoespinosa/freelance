package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import classes.ItemVenta;
import classes.Venta;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainViewTienda {

	private JFrame frame;
	private JFrame parent;

	private JList<ItemVenta> listPedido_2;

	private java.util.List<ItemVenta> compra = new ArrayList<>();
	JList<ItemVenta> listStock_2;

	Comparator<ItemVenta> comparatorPrecio = new Comparator<ItemVenta>() {

		@Override
		public int compare(ItemVenta o1, ItemVenta o2) {
			if (o1.getArticulo().getPrecioVenta() == o2.getArticulo().getPrecioVenta())
				return 0;
			else if (o1.getArticulo().getPrecioVenta() > o2.getArticulo().getPrecioVenta())
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

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public MainViewTienda(JFrame parent) {
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

		ButtonGroup bg = new ButtonGroup();

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Vender Articulos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblArticulosEnEl = new JLabel("Articulos en el Carrito");
		GridBagConstraints gbc_lblArticulosEnEl = new GridBagConstraints();
		gbc_lblArticulosEnEl.gridwidth = 2;
		gbc_lblArticulosEnEl.insets = new Insets(0, 0, 5, 0);
		gbc_lblArticulosEnEl.gridx = 4;
		gbc_lblArticulosEnEl.gridy = 0;
		panel_1.add(lblArticulosEnEl, gbc_lblArticulosEnEl);

		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 4;
		gbc_spinner_1.gridy = 1;
		panel_1.add(spinner_1, gbc_spinner_1);

		JButton btnAgregarAlCarrito = new JButton("Agregar al carrito");
		GridBagConstraints gbc_btnAgregarAlCarrito = new GridBagConstraints();
		gbc_btnAgregarAlCarrito.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregarAlCarrito.gridx = 5;
		gbc_btnAgregarAlCarrito.gridy = 1;
		panel_1.add(btnAgregarAlCarrito, gbc_btnAgregarAlCarrito);

		btnAgregarAlCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) listPedido_2.getModel();

				int cantReq = Integer.parseInt(spinner_1.getValue().toString());

				if (listStock_2.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(null, "Seleccione un producto.");

				} else if (cantReq <= 0 || cantReq > listStock_2.getSelectedValue().getCantidad()) {
					JOptionPane.showMessageDialog(null, "Verifique la cantidad ingresada.");
				} else
					listVenta.addElement(new ItemVenta(listStock_2.getSelectedValue().getArticulo(), cantReq));
			}

		});

		listStock_2 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_listStock_2 = new GridBagConstraints();
		gbc_listStock_2.gridheight = 2;
		gbc_listStock_2.fill = GridBagConstraints.BOTH;
		gbc_listStock_2.gridwidth = 2;
		gbc_listStock_2.insets = new Insets(0, 0, 5, 5);
		gbc_listStock_2.gridx = 1;
		gbc_listStock_2.gridy = 1;
		panel_1.add(listStock_2, gbc_listStock_2);

		listPedido_2 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list_3 = new GridBagConstraints();
		gbc_list_3.fill = GridBagConstraints.BOTH;
		gbc_list_3.gridwidth = 2;
		gbc_list_3.insets = new Insets(0, 0, 5, 0);
		gbc_list_3.gridx = 4;
		gbc_list_3.gridy = 2;
		panel_1.add(listPedido_2, gbc_list_3);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Efectivo", "Tarjeta Credito", "Tarjeta Debito" }));
		comboBox.setToolTipText("Forma de pago");
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		panel_1.add(comboBox, gbc_comboBox);

		JLabel lblTotalEnCarrito = new JLabel("Total en carrito:");
		GridBagConstraints gbc_lblTotalEnCarrito = new GridBagConstraints();
		gbc_lblTotalEnCarrito.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalEnCarrito.gridx = 4;
		gbc_lblTotalEnCarrito.gridy = 3;
		panel_1.add(lblTotalEnCarrito, gbc_lblTotalEnCarrito);

		JLabel lblTotalcarro = new JLabel("");
		GridBagConstraints gbc_lblTotalcarro = new GridBagConstraints();
		gbc_lblTotalcarro.insets = new Insets(0, 0, 5, 0);
		gbc_lblTotalcarro.gridx = 5;
		gbc_lblTotalcarro.gridy = 3;
		panel_1.add(lblTotalcarro, gbc_lblTotalcarro);

		JButton btnEjecutarVenta = new JButton("Ejecutar Venta");
		GridBagConstraints gbc_btnEjecutarVenta = new GridBagConstraints();
		gbc_btnEjecutarVenta.insets = new Insets(0, 0, 5, 0);
		gbc_btnEjecutarVenta.gridx = 5;
		gbc_btnEjecutarVenta.gridy = 4;
		panel_1.add(btnEjecutarVenta, gbc_btnEjecutarVenta);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				frame.setVisible(false);
				parent.setVisible(true);

			}
		});
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalir.gridx = 4;
		gbc_btnSalir.gridy = 5;
		panel_1.add(btnSalir, gbc_btnSalir);

		updateScreen();

		btnEjecutarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<ItemVenta> listaParaProveer = ((DefaultListModel<ItemVenta>) listPedido_2.getModel())
						.elements();
				List<ItemVenta> sTienda = InitView.getTienda().getArticulosEnStock();

				if (InitView.getTienda().getVentas() == null)
					InitView.getTienda().setVentas(new ArrayList<>());

				List<ItemVenta> factura = new ArrayList<>();
				InitView.getTienda().getVentas().add(new Venta(factura, System.currentTimeMillis(), "Impaga", "", ""));

				for (ItemVenta prov : Collections.list(listaParaProveer)) {
					if (sTienda.indexOf(prov) < 0) {
						sTienda.add(new ItemVenta(prov.getArticulo(), 0));
					}
					ItemVenta artDelStock = sTienda.get(sTienda.indexOf(prov));
					artDelStock.setCantidad(artDelStock.getCantidad() - prov.getCantidad());

					factura.add(new ItemVenta(prov.getArticulo(), prov.getCantidad()));
				}

				updateScreen();
				InitView.saveDB();

				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
				((DefaultListModel<ItemVenta>) listPedido_2.getModel()).clear();
			}
		});

	}

	private void updateView(JList<ItemVenta> list, Comparator<ItemVenta> c) {
		// listVenta.clear();

		List<ItemVenta> listOrdenada = new ArrayList<>();
		listOrdenada.addAll(InitView.getTienda().getArticulosEnStock());
		Collections.sort(listOrdenada, c);
		for (ItemVenta itemVenta : listOrdenada) {
			// listVenta.addElement(itemVenta);
		}
	}

	private void updateView(JList<ItemVenta> list) {
		
		DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) list.getModel();

		listVenta.clear();
		for (ItemVenta itemVenta : InitView.getTienda().getArticulosEnStock()) {
			listVenta.addElement(itemVenta);
		}

	}

	private void updateScreen() {
		updateView(listStock_2);
		listStock_2.updateUI();
	}

	private void updateVenta(JList<Venta> list) {
		// listVenta.clear();
		for (Venta venta : InitView.getTienda().getVentas()) {
			// listVenta.addElement(venta);
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
