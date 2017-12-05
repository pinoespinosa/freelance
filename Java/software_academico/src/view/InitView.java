package view;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import classes.Articulo;
import classes.ItemVenta;
import classes.Proveedor;
import classes.Tienda;
import classes.Venta;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InitView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	private static Tienda tienda = new Tienda();


	private MainViewAlmacen vistaAlmacen;
	private MainViewTienda vistaTienda;
	private MainViewProveedor vistaProveedor;
	


	/**
	 * Create the application.
	 */
	public InitView() {
		initialize();
		frame.setVisible(true);
		readDB();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton ingresoCliente = new JButton("Almacen");
		ingresoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.setVisible(false);
				if (vistaAlmacen==null)
					vistaAlmacen = new MainViewAlmacen(frame);
				else
					vistaAlmacen.getFrame().setVisible(true);
			}
		});
		GridBagConstraints gbc_ingresoCliente = new GridBagConstraints();
		gbc_ingresoCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_ingresoCliente.insets = new Insets(0, 0, 5, 5);
		gbc_ingresoCliente.gridx = 2;
		gbc_ingresoCliente.gridy = 1;
		frame.getContentPane().add(ingresoCliente, gbc_ingresoCliente);
		
		JButton ingresoTienda = new JButton("Ventas");
		ingresoTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.setVisible(false);
				if (vistaTienda==null)
					vistaTienda= new MainViewTienda(frame);
				else
					vistaTienda.getFrame().setVisible(true);

			}
		});
		GridBagConstraints gbc_ingresoTienda = new GridBagConstraints();
		gbc_ingresoTienda.fill = GridBagConstraints.HORIZONTAL;
		gbc_ingresoTienda.insets = new Insets(0, 0, 5, 5);
		gbc_ingresoTienda.gridx = 2;
		gbc_ingresoTienda.gridy = 2;
		frame.getContentPane().add(ingresoTienda, gbc_ingresoTienda);
		
		JButton ingresoProveedor = new JButton("Proveedores");
		ingresoProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				if (vistaProveedor==null)
					vistaProveedor= new MainViewProveedor(frame);
				else
					vistaProveedor.getFrame().setVisible(true);
				
			}
		});
		GridBagConstraints gbc_ingresoProveedor = new GridBagConstraints();
		gbc_ingresoProveedor.fill = GridBagConstraints.HORIZONTAL;
		gbc_ingresoProveedor.insets = new Insets(0, 0, 5, 5);
		gbc_ingresoProveedor.gridx = 2;
		gbc_ingresoProveedor.gridy = 3;
		frame.getContentPane().add(ingresoProveedor, gbc_ingresoProveedor);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
	
				frame.dispose();	
	
			}
		});
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.gridx = 3;
		gbc_btnSalir.gridy = 5;
		frame.getContentPane().add(btnSalir, gbc_btnSalir);
	}

	
	
	public static Tienda getTienda() {
		return tienda;
	}

	public static void setTienda(Tienda tienda) {
		InitView.tienda = tienda;
	}
	
	public static void readDB(){
		try {
			readFromFile("baseDatos.DAT");

		} catch (Exception e) {
			e.printStackTrace();
			tienda.setArticuloOrdenados(new ArrayList<>());
			tienda.getArticuloOrdenados().add(new ItemVenta(new Articulo("11","Zapallo", 500, 600,"Manuel Garcia"), 10));

			tienda.setArticulosEnStock(new ArrayList<>());
			tienda.getArticulosEnStock().add(new ItemVenta(new Articulo("12","Pure", 500,750,"Manuel Garcia"), 20));
			tienda.getArticulosEnStock().add(new ItemVenta(new Articulo("13","Maicena", 200,300,"Manuel Garcia"), 20));
			tienda.getArticulosEnStock().add(new ItemVenta(new Articulo("14","Tomate", 400,500,"Manuel Garcia"), 20));
			
			tienda.setVentas(new ArrayList<>());
			tienda.getVentas().add(new Venta( Arrays.asList(new ItemVenta(new Articulo("12","Salsa", 16,25,"Manuel Garcia"), 1)),System.currentTimeMillis(),"Creada","Detalle",""));
			tienda.getVentas().add(new Venta( Arrays.asList(new ItemVenta(new Articulo("12","Caramelos", 16,25,"Manuel Garcia"), 1), new ItemVenta(new Articulo("12","Golosinas", 16,25,"Manuel Garcia"), 1), new ItemVenta(new Articulo("12","Chicles", 16,25,"Manuel Garcia"), 1)),System.currentTimeMillis(),"Preparada","",""));
			tienda.getVentas().add(new Venta( Arrays.asList(new ItemVenta(new Articulo("12","Caramelos", 16,25,"Manuel Garcia"), 1)),System.currentTimeMillis(),"Paga","Cheque Numero 1057423","Cheque"));
			
			
			tienda.setProveedores(new ArrayList<>());
			tienda.getProveedores().add(new Proveedor("Manuel Garcia", "Belgrano nro. 123", "0303456", "aa@pino.test", "12345"));
			tienda.getProveedores().add(new Proveedor("Manuel Gonzales", "San Martin nro. 123", "0303456", "aa@pino.test", "12345"));

			
			infoToFile(tienda, "baseDatos.DAT");

		}
	}

	public static void saveDB(){
		try {
			infoToFile(tienda,"baseDatos.DAT");
		} catch (Exception e) {
		}
	}

	private static void readFromFile(String filename) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();
		InitView.setTienda(mapper.readValue(new File(filename), Tienda.class));

	}

	private static void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}