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

public class MainViewTienda {

	private JFrame frame;
	private JFrame parent;

	private java.util.List<ItemVenta> compra = new ArrayList<>();
	private JTextField textField;
	
	JList<ItemVenta> listStock;
	JList<ItemVenta> listStock_2;
	JList<ItemVenta> listStock7;
	
	
	
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
	JList<Venta> list_ventas;
	private JTextField nombreProd;
	
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
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
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

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Ordenar Articulos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblArticulosParaOrdenar = new JLabel("Articulos para Ordenar");
		GridBagConstraints gbc_lblArticulosParaOrdenar = new GridBagConstraints();
		gbc_lblArticulosParaOrdenar.gridwidth = 2;
		gbc_lblArticulosParaOrdenar.insets = new Insets(0, 0, 5, 5);
		gbc_lblArticulosParaOrdenar.gridx = 1;
		gbc_lblArticulosParaOrdenar.gridy = 0;
		panel.add(lblArticulosParaOrdenar, gbc_lblArticulosParaOrdenar);

		JLabel lblPedidoParaOrdenar = new JLabel("Pedido para ordenar");
		GridBagConstraints gbc_lblPedidoParaOrdenar = new GridBagConstraints();
		gbc_lblPedidoParaOrdenar.gridwidth = 2;
		gbc_lblPedidoParaOrdenar.insets = new Insets(0, 0, 5, 5);
		gbc_lblPedidoParaOrdenar.gridx = 4;
		gbc_lblPedidoParaOrdenar.gridy = 0;
		panel.add(lblPedidoParaOrdenar, gbc_lblPedidoParaOrdenar);

		listStock = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_listStock = new GridBagConstraints();
		gbc_listStock.gridwidth = 2;
		gbc_listStock.insets = new Insets(0, 0, 5, 5);
		gbc_listStock.fill = GridBagConstraints.BOTH;
		gbc_listStock.gridx = 1;
		gbc_listStock.gridy = 1;
		panel.add(listStock, gbc_listStock);

		JList<ItemVenta> listPedido_1 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridwidth = 2;
		gbc_list_1.insets = new Insets(0, 0, 5, 0);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 4;
		gbc_list_1.gridy = 1;
		panel.add(listPedido_1, gbc_list_1);

		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 2;
		panel.add(spinner, gbc_spinner);

		JButton btnAgregarAlCarro = new JButton("Agregar al pedido");
		GridBagConstraints gbc_btnAgregarAlCarro = new GridBagConstraints();
		gbc_btnAgregarAlCarro.insets = new Insets(0, 0, 0, 5);
		gbc_btnAgregarAlCarro.gridx = 2;
		gbc_btnAgregarAlCarro.gridy = 2;
		panel.add(btnAgregarAlCarro, gbc_btnAgregarAlCarro);

		JButton btnEnviarPedido = new JButton("Enviar pedido");
		GridBagConstraints gbc_btnEnviarPedido = new GridBagConstraints();
		gbc_btnEnviarPedido.gridx = 5;
		gbc_btnEnviarPedido.gridy = 2;
		panel.add(btnEnviarPedido, gbc_btnEnviarPedido);

		btnAgregarAlCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) listPedido_1.getModel();

				int cantReq = Integer.parseInt(spinner.getValue().toString());

				if (listStock.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(null, "Seleccione un producto.");

				} else if (cantReq <= 0 || cantReq > listStock.getSelectedValue().getCantidad()) {
					JOptionPane.showMessageDialog(null, "Verifique la cantidad ingresada.");
				} else
					listVenta.addElement(new ItemVenta(listStock.getSelectedValue().getArticulo(), cantReq));

			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Administrar Articulos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JRadioButton rdnPrecio = new JRadioButton("Por Precio");
		rdnPrecio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (rdnPrecio.isSelected()) {
					{

						comparatorSelected = comparatorPrecio;
						updateScreen();
					}
				}

			}
		});
		GridBagConstraints gbc_rdnPrecio = new GridBagConstraints();
		gbc_rdnPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_rdnPrecio.gridx = 1;
		gbc_rdnPrecio.gridy = 0;
		panel_3.add(rdnPrecio, gbc_rdnPrecio);
		
		JRadioButton rdnProducto = new JRadioButton("Por Producto");
		rdnProducto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			

				if (rdnProducto.isSelected()) {
					{

						comparatorSelected = comparatorNombre;
						updateScreen();
					}
				}
			
			}
		});
		GridBagConstraints gbc_rdnProducto = new GridBagConstraints();
		gbc_rdnProducto.insets = new Insets(0, 0, 5, 5);
		gbc_rdnProducto.gridx = 2;
		gbc_rdnProducto.gridy = 0;
		panel_3.add(rdnProducto, gbc_rdnProducto);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdnProducto);
		bg.add(rdnPrecio);
		
		JLabel lblPrecio = new JLabel("Precio");
		GridBagConstraints gbc_lblPrecio = new GridBagConstraints();
		gbc_lblPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecio.gridx = 4;
		gbc_lblPrecio.gridy = 0;
		panel_3.add(lblPrecio, gbc_lblPrecio);
		
		JSpinner precioProd = new JSpinner();
		GridBagConstraints gbc_precioProd = new GridBagConstraints();
		gbc_precioProd.fill = GridBagConstraints.HORIZONTAL;
		gbc_precioProd.insets = new Insets(0, 0, 5, 5);
		gbc_precioProd.gridx = 5;
		gbc_precioProd.gridy = 0;
		panel_3.add(precioProd, gbc_precioProd);
		
		listStock7 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		listStock7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (listStock7.getSelectedValue() != null) {
					nombreProd.setText(listStock7.getSelectedValue().getArticulo().getNombre());
					precioProd.setValue(listStock7.getSelectedValue().getArticulo().getPrecioVenta());

				}
			}
		});
		GridBagConstraints gbc_listStock7 = new GridBagConstraints();
		gbc_listStock7.gridwidth = 2;
		gbc_listStock7.gridheight = 3;
		gbc_listStock7.fill = GridBagConstraints.BOTH;
		gbc_listStock7.insets = new Insets(0, 0, 0, 5);
		gbc_listStock7.gridx = 1;
		gbc_listStock7.gridy = 1;
		panel_3.add(listStock7, gbc_listStock7);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 4;
		gbc_lblNombre.gridy = 1;
		panel_3.add(lblNombre, gbc_lblNombre);
		
		nombreProd = new JTextField();
		GridBagConstraints gbc_nombreProd = new GridBagConstraints();
		gbc_nombreProd.insets = new Insets(0, 0, 5, 5);
		gbc_nombreProd.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreProd.gridx = 5;
		gbc_nombreProd.gridy = 1;
		panel_3.add(nombreProd, gbc_nombreProd);
		nombreProd.setColumns(10);
		
		JButton btnActualizarDatos = new JButton("Actualizar datos");
		btnActualizarDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (listStock7.getSelectedValue() != null) {
					
					listStock7.getSelectedValue().getArticulo().setNombre(nombreProd.getText());;
					listStock7.getSelectedValue().getArticulo().setPrecioVenta((float) precioProd.getValue());;
					
					nombreProd.setText("");
					precioProd.setValue(0);
					
					updateScreen();
				}				
				
			}
		});
		GridBagConstraints gbc_btnActualizarDatos = new GridBagConstraints();
		gbc_btnActualizarDatos.gridwidth = 2;
		gbc_btnActualizarDatos.insets = new Insets(0, 0, 0, 5);
		gbc_btnActualizarDatos.gridx = 4;
		gbc_btnActualizarDatos.gridy = 3;
		panel_3.add(btnActualizarDatos, gbc_btnActualizarDatos);
	
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Vender Articulos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblArticulosParaVender = new JLabel("Articulos para Vender");
		GridBagConstraints gbc_lblArticulosParaVender = new GridBagConstraints();
		gbc_lblArticulosParaVender.gridwidth = 2;
		gbc_lblArticulosParaVender.insets = new Insets(0, 0, 5, 5);
		gbc_lblArticulosParaVender.gridx = 1;
		gbc_lblArticulosParaVender.gridy = 0;
		panel_1.add(lblArticulosParaVender, gbc_lblArticulosParaVender);
		
		JLabel lblArticulosEnEl = new JLabel("Articulos en el Carrito");
		GridBagConstraints gbc_lblArticulosEnEl = new GridBagConstraints();
		gbc_lblArticulosEnEl.gridwidth = 2;
		gbc_lblArticulosEnEl.insets = new Insets(0, 0, 5, 5);
		gbc_lblArticulosEnEl.gridx = 4;
		gbc_lblArticulosEnEl.gridy = 0;
		panel_1.add(lblArticulosEnEl, gbc_lblArticulosEnEl);
		
		listStock_2 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_listStock_2 = new GridBagConstraints();
		gbc_listStock_2.fill = GridBagConstraints.BOTH;
		gbc_listStock_2.gridwidth = 2;
		gbc_listStock_2.insets = new Insets(0, 0, 5, 5);
		gbc_listStock_2.gridx = 1;
		gbc_listStock_2.gridy = 1;
		panel_1.add(listStock_2, gbc_listStock_2);
		
		JList<ItemVenta> listPedido_2 = new JList<ItemVenta>(new DefaultListModel<ItemVenta>());
		GridBagConstraints gbc_list_3 = new GridBagConstraints();
		gbc_list_3.fill = GridBagConstraints.BOTH;
		gbc_list_3.gridwidth = 2;
		gbc_list_3.insets = new Insets(0, 0, 5, 0);
		gbc_list_3.gridx = 4;
		gbc_list_3.gridy = 1;
		panel_1.add(listPedido_2, gbc_list_3);
		
		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 2;
		panel_1.add(spinner_1, gbc_spinner_1);
		
		JButton btnAgregarAlCarrito = new JButton("Agregar al carrito");
		GridBagConstraints gbc_btnAgregarAlCarrito = new GridBagConstraints();
		gbc_btnAgregarAlCarrito.insets = new Insets(0, 0, 0, 5);
		gbc_btnAgregarAlCarrito.gridx = 2;
		gbc_btnAgregarAlCarrito.gridy = 2;
		panel_1.add(btnAgregarAlCarrito, gbc_btnAgregarAlCarrito);
		
		JButton btnEjecutarVenta = new JButton("Ejecutar Venta");
		GridBagConstraints gbc_btnEjecutarVenta = new GridBagConstraints();
		gbc_btnEjecutarVenta.gridx = 5;
		gbc_btnEjecutarVenta.gridy = 2;
		panel_1.add(btnEjecutarVenta, gbc_btnEjecutarVenta);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Preparar factura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 4;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		list_ventas = new JList<Venta>(new DefaultListModel<Venta>());
		GridBagConstraints gbc_list_ventas = new GridBagConstraints();
		gbc_list_ventas.gridheight = 2;
		gbc_list_ventas.fill = GridBagConstraints.BOTH;
		gbc_list_ventas.insets = new Insets(0, 0, 5, 5);
		gbc_list_ventas.gridx = 1;
		gbc_list_ventas.gridy = 0;
		panel_2.add(list_ventas, gbc_list_ventas);
		
		JLabel lblComentarios = new JLabel("Comentarios");
		GridBagConstraints gbc_lblComentarios = new GridBagConstraints();
		gbc_lblComentarios.insets = new Insets(0, 0, 5, 0);
		gbc_lblComentarios.gridx = 3;
		gbc_lblComentarios.gridy = 0;
		panel_2.add(lblComentarios, gbc_lblComentarios);
		
		textField = new JTextField();
		textField.setToolTipText("Detalle");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnRegistrarPago = new JButton("Preparar factura");
		GridBagConstraints gbc_btnRegistrarPago = new GridBagConstraints();
		gbc_btnRegistrarPago.gridx = 3;
		gbc_btnRegistrarPago.gridy = 2;
		panel_2.add(btnRegistrarPago, gbc_btnRegistrarPago);
		
		
		
		updateScreen();

		btnRegistrarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if ( list_ventas.getSelectedValue() != null) {

					if ("Creada".equals(list_ventas.getSelectedValue().getEstado())) {
						list_ventas.getSelectedValue().setEstado("Preparada");
						JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
						updateScreen();
					}
				 else
					JOptionPane.showMessageDialog(null, "Solo se pueden preparar facturas \"creadas\"...");
			}
			}
		});

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

		btnEjecutarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<ItemVenta> listaParaProveer = ((DefaultListModel<ItemVenta>) listPedido_2.getModel()).elements();
				List<ItemVenta> sTienda = InitView.getTienda().getArticulosEnStock();
				
				if (InitView.getTienda().getVentas()==null)
					InitView.getTienda().setVentas(new ArrayList<>());
				
				List<ItemVenta> factura = new ArrayList<>();
				InitView.getTienda().getVentas().add(new Venta(factura, System.currentTimeMillis(),"Impaga","",""));
				
				for (ItemVenta prov : Collections.list(listaParaProveer)) {					
					if (sTienda.indexOf(prov) < 0) {
						sTienda.add(new ItemVenta(prov.getArticulo(), 0));
					}
					ItemVenta artDelStock = sTienda.get(sTienda.indexOf(prov));
					artDelStock.setCantidad( artDelStock.getCantidad() - prov.getCantidad());
					
					factura.add(new ItemVenta(prov.getArticulo(), prov.getCantidad()));
				}

				

				
				updateScreen();
				
				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
				((DefaultListModel<ItemVenta>) listPedido_2.getModel()).clear();
			}
		});

		btnEnviarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<ItemVenta> listaParaProveer = ((DefaultListModel<ItemVenta>) listPedido_1.getModel()).elements();
				List<ItemVenta> pTienda = InitView.getTienda().getArticuloOrdenados();
				
				for (ItemVenta prov : Collections.list(listaParaProveer)) {				
					
					if (pTienda.indexOf(prov) < 0) {
						pTienda.add(new ItemVenta(prov.getArticulo(), 0));
					}
					
					ItemVenta artDelProv = pTienda.get(pTienda.indexOf(prov));
					artDelProv.setCantidad(artDelProv.getCantidad()+prov.getCantidad());				
				}



				
				JOptionPane.showMessageDialog(null, "La operación se ha realizado exitosamente");
				((DefaultListModel<ItemVenta>) listPedido_2.getModel()).clear();
			}
		});

		
		
	}

	private void updateView(JList<ItemVenta> list, Comparator<ItemVenta> c){
		
		DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) list.getModel();
		listVenta.clear();
		
		List<ItemVenta> listOrdenada = new ArrayList<>();
		listOrdenada.addAll(InitView.getTienda().getArticulosEnStock());
		Collections.sort(listOrdenada,c);
		for (ItemVenta itemVenta : listOrdenada) {
			listVenta.addElement(itemVenta);
		}
	}

	
	private void updateView(JList<ItemVenta> list){
		
		DefaultListModel<ItemVenta> listVenta = (DefaultListModel<ItemVenta>) list.getModel();
		listVenta.clear();
		for (ItemVenta itemVenta : InitView.getTienda().getArticulosEnStock()) {
			listVenta.addElement(itemVenta);
		}
		
	}

	private void updateScreen(){
		updateView(listStock);
		updateView(listStock_2);
		
		updateView(listStock7,comparatorSelected);
		updateVenta(list_ventas);
		
		listStock.updateUI();
		listStock_2.updateUI();
		listStock7.updateUI();
		list_ventas.updateUI();	
	}
	
	private void updateVenta(JList<Venta> list){
		
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
