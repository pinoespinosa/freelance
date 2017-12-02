package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

import classes.Articulo;
import classes.ItemVenta;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainViewProveedor2 {

	private JFrame frame;
	private JFrame parent;

	private java.util.List<ItemVenta> compra = new ArrayList<>();

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public MainViewProveedor2(JFrame parent) {
		initialize();
		frame.setVisible(true);
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
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
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
		gbc_btnVolver.gridx = 4;
		gbc_btnVolver.gridy = 0;
		frame.getContentPane().add(btnVolver, gbc_btnVolver);

		JLabel lblArticulo = new JLabel("Articulos solicitados por Tienda");
		GridBagConstraints gbc_lblArticulo = new GridBagConstraints();
		gbc_lblArticulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblArticulo.gridx = 2;
		gbc_lblArticulo.gridy = 2;
		frame.getContentPane().add(lblArticulo, gbc_lblArticulo);

		JLabel lblProductosEnEl = new JLabel("Articulos a proveer");
		GridBagConstraints gbc_lblProductosEnEl = new GridBagConstraints();
		gbc_lblProductosEnEl.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductosEnEl.gridx = 4;
		gbc_lblProductosEnEl.gridy = 2;
		frame.getContentPane().add(lblProductosEnEl, gbc_lblProductosEnEl);

		JList<ItemVenta> list = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 2;
		gbc_list.gridy = 3;
		frame.getContentPane().add(list, gbc_list);

		JList<ItemVenta> list_1 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridheight = 3;
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 4;
		gbc_list_1.gridy = 3;
		frame.getContentPane().add(list_1, gbc_list_1);

		JLabel lblCantidad = new JLabel("Cantidad");
		GridBagConstraints gbc_lblCantidad = new GridBagConstraints();
		gbc_lblCantidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidad.anchor = GridBagConstraints.EAST;
		gbc_lblCantidad.gridx = 1;
		gbc_lblCantidad.gridy = 4;
		frame.getContentPane().add(lblCantidad, gbc_lblCantidad);

		JButton btnAgregarAlCarro = new JButton("Agregar al carro");

		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 4;

		frame.getContentPane().add(spinner, gbc_spinner);
		GridBagConstraints gbc_btnAgregarAlCarro = new GridBagConstraints();
		gbc_btnAgregarAlCarro.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAgregarAlCarro.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarAlCarro.gridx = 2;
		gbc_btnAgregarAlCarro.gridy = 5;
		frame.getContentPane().add(btnAgregarAlCarro, gbc_btnAgregarAlCarro);

		JButton proveerTienda = new JButton("Proveer");

		GridBagConstraints gbc_proveerTienda = new GridBagConstraints();
		gbc_proveerTienda.fill = GridBagConstraints.HORIZONTAL;
		gbc_proveerTienda.insets = new Insets(0, 0, 5, 5);
		gbc_proveerTienda.gridx = 4;
		gbc_proveerTienda.gridy = 6;
		frame.getContentPane().add(proveerTienda, gbc_proveerTienda);

		btnAgregarAlCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) list_1.getModel();

				int cantReq = Integer.parseInt(spinner.getValue().toString());

				if (cantReq > 0 && list.getSelectedValue() != null
						&& cantReq <= list.getSelectedValue().getCantidad()) {
					listVenta.addElement(new ItemVenta(list.getSelectedValue().getArticulo(), cantReq));
				}

			}
		});

		proveerTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<ItemVenta> listaParaProveer = ((DefaultListModel<ItemVenta>) list_1.getModel()).elements();
				Hashtable<Articulo, Integer> sTienda = InitView.getTienda().getArticulosEnStock();
				Hashtable<Articulo, Integer> pTienda = InitView.getTienda().getArticuloOrdenados();

				
				for (ItemVenta prov : Collections.list(listaParaProveer)) {
					int cantStock = sTienda.containsKey(prov.getArticulo()) ? sTienda.get(prov.getArticulo()) : 0;
					sTienda.put(prov.getArticulo(), prov.getCantidad() + cantStock);

					int stockPed = pTienda.containsKey(prov.getArticulo()) ? pTienda.get(prov.getArticulo()) : 0;
					pTienda.put(prov.getArticulo(), stockPed - prov.getCantidad());

				}

				list_1.updateUI();
				list.updateUI();
				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
				((DefaultListModel<ItemVenta>) list_1.getModel()).clear();
			}
		});

		for (Articulo art : InitView.getTienda().getArticuloOrdenados().keySet()) {
			int cant = InitView.getTienda().getArticuloOrdenados().get(art);
			if (cant > 0) {
				((DefaultListModel) list.getModel()).addElement(new ItemVenta(art, cant));
			}
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
