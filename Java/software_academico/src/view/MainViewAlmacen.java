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
import java.util.List;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

import classes.Articulo;
import classes.ItemVenta;
import classes.Proveedor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListModel;

public class MainViewAlmacen {

	private JFrame frame;
	private JFrame parent;

	private java.util.List<ItemVenta> compra = new ArrayList<>();
	private JTextField fNombre;
	private JTextField fCantidad;
	private JTextField fPrecioCompra;
	private JTextField fPrecioVenta;
	private JTextField fProveedor;

	private boolean editando;
	private JTextField fCodigo;

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public MainViewAlmacen(JFrame parent) {
		initialize();
		frame.setVisible(true);
		this.parent = parent;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 521);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 0, 0, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0, 0, 15, 0, 15, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblCodigo = new JLabel("Codigo");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 1;
		gbc_lblCodigo.gridy = 1;
		frame.getContentPane().add(lblCodigo, gbc_lblCodigo);
		
		fCodigo = new JTextField();
		GridBagConstraints gbc_fCodigo = new GridBagConstraints();
		gbc_fCodigo.anchor = GridBagConstraints.SOUTH;
		gbc_fCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_fCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_fCodigo.gridx = 2;
		gbc_fCodigo.gridy = 1;
		frame.getContentPane().add(fCodigo, gbc_fCodigo);
		fCodigo.setColumns(10);

		JLabel lblNombre_1 = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
		gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombre_1.gridx = 1;
		gbc_lblNombre_1.gridy = 2;
		frame.getContentPane().add(lblNombre_1, gbc_lblNombre_1);

		fNombre = new JTextField();
		GridBagConstraints gbc_fNombre = new GridBagConstraints();
		gbc_fNombre.insets = new Insets(0, 0, 5, 5);
		gbc_fNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_fNombre.gridx = 2;
		gbc_fNombre.gridy = 2;
		frame.getContentPane().add(fNombre, gbc_fNombre);
		fNombre.setColumns(10);
		
		JList<ItemVenta> list_1 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				fNombre.setText(list_1.getSelectedValue().getArticulo().getNombre());
				fCodigo.setText(list_1.getSelectedValue().getArticulo().getCodigo());
				fCantidad.setText(list_1.getSelectedValue().getCantidad()+"");
				fPrecioCompra.setText(list_1.getSelectedValue().getArticulo().getPrecioCompra()+"");
				fPrecioVenta.setText(list_1.getSelectedValue().getArticulo().getPrecioVenta()+"");
				
				
			}
		});
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridheight = 6;
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 3;
		gbc_list_1.gridy = 1;
		frame.getContentPane().add(list_1, gbc_list_1);

		JLabel lblNombre = new JLabel("Cantidad");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		frame.getContentPane().add(lblNombre, gbc_lblNombre);

		fCantidad = new JTextField();
		GridBagConstraints gbc_fCantidad = new GridBagConstraints();
		gbc_fCantidad.insets = new Insets(0, 0, 5, 5);
		gbc_fCantidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_fCantidad.gridx = 2;
		gbc_fCantidad.gridy = 3;
		frame.getContentPane().add(fCantidad, gbc_fCantidad);
		fCantidad.setColumns(10);

		JLabel lblTelefono = new JLabel("Precio de compra");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 4;
		frame.getContentPane().add(lblTelefono, gbc_lblTelefono);

		fPrecioCompra = new JTextField();
		GridBagConstraints gbc_fPrecioCompra = new GridBagConstraints();
		gbc_fPrecioCompra.insets = new Insets(0, 0, 5, 5);
		gbc_fPrecioCompra.fill = GridBagConstraints.HORIZONTAL;
		gbc_fPrecioCompra.gridx = 2;
		gbc_fPrecioCompra.gridy = 4;
		frame.getContentPane().add(fPrecioCompra, gbc_fPrecioCompra);
		fPrecioCompra.setColumns(10);

		JLabel lblEmail = new JLabel("Precio de venta");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 5;
		frame.getContentPane().add(lblEmail, gbc_lblEmail);

		fPrecioVenta = new JTextField();
		GridBagConstraints gbc_fPrecioVenta = new GridBagConstraints();
		gbc_fPrecioVenta.insets = new Insets(0, 0, 5, 5);
		gbc_fPrecioVenta.fill = GridBagConstraints.HORIZONTAL;
		gbc_fPrecioVenta.gridx = 2;
		gbc_fPrecioVenta.gridy = 5;
		frame.getContentPane().add(fPrecioVenta, gbc_fPrecioVenta);
		fPrecioVenta.setColumns(10);

		JLabel lblRfc = new JLabel("Proveedor");
		GridBagConstraints gbc_lblRfc = new GridBagConstraints();
		gbc_lblRfc.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblRfc.insets = new Insets(0, 0, 5, 5);
		gbc_lblRfc.gridx = 1;
		gbc_lblRfc.gridy = 6;
		frame.getContentPane().add(lblRfc, gbc_lblRfc);

		fProveedor = new JTextField();
		GridBagConstraints gbc_fProveedor = new GridBagConstraints();
		gbc_fProveedor.anchor = GridBagConstraints.NORTH;
		gbc_fProveedor.insets = new Insets(0, 0, 5, 5);
		gbc_fProveedor.fill = GridBagConstraints.HORIZONTAL;
		gbc_fProveedor.gridx = 2;
		gbc_fProveedor.gridy = 6;
		frame.getContentPane().add(fProveedor, gbc_fProveedor);
		fProveedor.setColumns(10);

		JList<Proveedor> list = new JList<Proveedor>(new DefaultListModel<Proveedor>());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fProveedor.setText(list.getSelectedValue().toString());
			}
		});

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 7;
		frame.getContentPane().add(list, gbc_list);

		updateView(list);
		updateViewVenta(list_1);

		JButton btnEditarProveedor = new JButton("Editar");

		GridBagConstraints gbc_btnEditarProveedor = new GridBagConstraints();
		gbc_btnEditarProveedor.anchor = GridBagConstraints.EAST;
		gbc_btnEditarProveedor.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditarProveedor.gridx = 1;
		gbc_btnEditarProveedor.gridy = 9;
		frame.getContentPane().add(btnEditarProveedor, gbc_btnEditarProveedor);

		JButton btnVolver = new JButton("Salir");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				frame.setVisible(false);
				parent.setVisible(true);

			}
		});

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ItemVenta p = list_1.getSelectedValue();

				InitView.getTienda().getArticulosEnStock().remove(p);

				updateViewVenta(list_1);
				list_1.updateUI();
				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");

			}
		});
		GridBagConstraints gbc_btnBorrar = new GridBagConstraints();
		gbc_btnBorrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorrar.gridx = 2;
		gbc_btnBorrar.gridy = 9;
		frame.getContentPane().add(btnBorrar, gbc_btnBorrar);
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.anchor = GridBagConstraints.NORTH;
		gbc_btnVolver.gridx = 3;
		gbc_btnVolver.gridy = 9;
		frame.getContentPane().add(btnVolver, gbc_btnVolver);

		btnEditarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ItemVenta it = list_1.getSelectedValue();

				it.getArticulo().setCodigo(fCodigo.getText());
				it.getArticulo().setNombre(fNombre.getText());
				it.getArticulo().setPrecioCompra(Float.parseFloat(fPrecioCompra.getText()));
				it.getArticulo().setPrecioVenta(Float.parseFloat(fPrecioVenta.getText()));
				
				updateView(list);
				updateViewVenta(list_1);
				list.updateUI();
				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
			}
		});

	}

	private void updateView(JList<Proveedor> list) {

		DefaultListModel<Proveedor> listP = (DefaultListModel<Proveedor>) list.getModel();
		listP.clear();
		for (Proveedor p : InitView.getTienda().getProveedores()) {
			listP.addElement(p);
		}

	}
	private void updateViewVenta(JList<ItemVenta> list) {

		DefaultListModel<ItemVenta> listP = (DefaultListModel<ItemVenta>) list.getModel();
		listP.clear();
		for (ItemVenta p : InitView.getTienda().getArticulosEnStock()) {
			listP.addElement(p);
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
