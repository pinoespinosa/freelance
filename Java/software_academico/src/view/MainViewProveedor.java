package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.deser.ValueInstantiator;

import classes.ItemVenta;
import classes.Proveedor;

public class MainViewProveedor {

	private JFrame frame;
	private JFrame parent;

	private java.util.List<ItemVenta> compra = new ArrayList<>();
	private JTextField fNombre;
	private JTextField fDireccion;
	private JTextField fTelefono;
	private JTextField fEmail;
	private JTextField fRFC;
	private ViewArticulosProveedor vistaArticulos;

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public MainViewProveedor(JFrame parent) {
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
		gridBagLayout.columnWidths = new int[] { 15, 0, 0, 0, 0, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0, 15, 0, 15, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblNombre_1 = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
		gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombre_1.gridx = 1;
		gbc_lblNombre_1.gridy = 1;
		frame.getContentPane().add(lblNombre_1, gbc_lblNombre_1);

		fNombre = new JTextField();
		GridBagConstraints gbc_fNombre = new GridBagConstraints();
		gbc_fNombre.gridwidth = 2;
		gbc_fNombre.insets = new Insets(0, 0, 5, 5);
		gbc_fNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_fNombre.gridx = 2;
		gbc_fNombre.gridy = 1;
		frame.getContentPane().add(fNombre, gbc_fNombre);
		fNombre.setColumns(10);

		JLabel lblNombre = new JLabel("Direccion");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		frame.getContentPane().add(lblNombre, gbc_lblNombre);

		fDireccion = new JTextField();
		GridBagConstraints gbc_fDireccion = new GridBagConstraints();
		gbc_fDireccion.gridwidth = 2;
		gbc_fDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_fDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_fDireccion.gridx = 2;
		gbc_fDireccion.gridy = 2;
		frame.getContentPane().add(fDireccion, gbc_fDireccion);
		fDireccion.setColumns(10);

		JLabel lblTelefono = new JLabel("Telefono");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 3;
		frame.getContentPane().add(lblTelefono, gbc_lblTelefono);

		fTelefono = new JTextField();
		GridBagConstraints gbc_fTelefono = new GridBagConstraints();
		gbc_fTelefono.gridwidth = 2;
		gbc_fTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_fTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_fTelefono.gridx = 2;
		gbc_fTelefono.gridy = 3;
		frame.getContentPane().add(fTelefono, gbc_fTelefono);
		fTelefono.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 4;
		frame.getContentPane().add(lblEmail, gbc_lblEmail);

		fEmail = new JTextField();
		GridBagConstraints gbc_fEmail = new GridBagConstraints();
		gbc_fEmail.gridwidth = 2;
		gbc_fEmail.insets = new Insets(0, 0, 5, 5);
		gbc_fEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_fEmail.gridx = 2;
		gbc_fEmail.gridy = 4;
		frame.getContentPane().add(fEmail, gbc_fEmail);
		fEmail.setColumns(10);

		JLabel lblRfc = new JLabel("RFC");
		GridBagConstraints gbc_lblRfc = new GridBagConstraints();
		gbc_lblRfc.anchor = GridBagConstraints.EAST;
		gbc_lblRfc.insets = new Insets(0, 0, 5, 5);
		gbc_lblRfc.gridx = 1;
		gbc_lblRfc.gridy = 5;
		frame.getContentPane().add(lblRfc, gbc_lblRfc);

		fRFC = new JTextField();
		GridBagConstraints gbc_fRFC = new GridBagConstraints();
		gbc_fRFC.gridwidth = 2;
		gbc_fRFC.insets = new Insets(0, 0, 5, 5);
		gbc_fRFC.fill = GridBagConstraints.HORIZONTAL;
		gbc_fRFC.gridx = 2;
		gbc_fRFC.gridy = 5;
		frame.getContentPane().add(fRFC, gbc_fRFC);
		fRFC.setColumns(10);

		JList<Proveedor> list = new JList<Proveedor>(new DefaultListModel<Proveedor>());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fNombre.setText(list.getSelectedValue().getNombre());
				fDireccion.setText(list.getSelectedValue().getDireccion());
				fEmail.setText(list.getSelectedValue().getEmail());
				fTelefono.setText(list.getSelectedValue().getTelefono());
				fRFC.setText(list.getSelectedValue().getRFC());
			}
		});

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 6;
		frame.getContentPane().add(list, gbc_list);

		updateView(list);

		JButton btnEditarProveedor = new JButton("Editar");

		GridBagConstraints gbc_btnEditarProveedor = new GridBagConstraints();
		gbc_btnEditarProveedor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditarProveedor.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditarProveedor.gridx = 1;
		gbc_btnEditarProveedor.gridy = 8;
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

				Proveedor p = list.getSelectedValue();

				InitView.getTienda().getProveedores().remove(p);

				updateView(list);
				list.updateUI();
				InitView.saveDB();

				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");

			}
		});
		GridBagConstraints gbc_btnBorrar = new GridBagConstraints();
		gbc_btnBorrar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBorrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorrar.gridx = 2;
		gbc_btnBorrar.gridy = 8;
		frame.getContentPane().add(btnBorrar, gbc_btnBorrar);
		
		JButton btnVerArticulos = new JButton("Ver Articulos");
		btnVerArticulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (list.getSelectedValue() == null)
					JOptionPane.showMessageDialog(null, "Seleccione un proveedor.");
				else {
					frame.setVisible(false);
					if (vistaArticulos == null)
						vistaArticulos = new ViewArticulosProveedor(frame, list.getSelectedValue());
					else {

						vistaArticulos.getFrame().setVisible(true);
						vistaArticulos.update(list.getSelectedValue());
					}
				}
			}
		});
		GridBagConstraints gbc_btnVerArticulos = new GridBagConstraints();
		gbc_btnVerArticulos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVerArticulos.insets = new Insets(0, 0, 5, 5);
		gbc_btnVerArticulos.gridx = 3;
		gbc_btnVerArticulos.gridy = 8;
		frame.getContentPane().add(btnVerArticulos, gbc_btnVerArticulos);
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.anchor = GridBagConstraints.NORTH;
		gbc_btnVolver.gridx = 4;
		gbc_btnVolver.gridy = 8;
		frame.getContentPane().add(btnVolver, gbc_btnVolver);

		btnEditarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Proveedor p = list.getSelectedValue();
				p.setNombre(fNombre.getText());
				p.setDireccion(fDireccion.getText());
				p.setEmail(fEmail.getText());
				p.setRFC(fRFC.getText());
				p.setTelefono(fTelefono.getText());

				updateView(list);
				list.updateUI();
				InitView.saveDB();
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
