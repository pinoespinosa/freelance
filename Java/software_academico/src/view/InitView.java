package view;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import classes.Articulo;
import classes.ItemVenta;
import classes.Tienda;

public class InitView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	private static Tienda tienda = new Tienda();


	private MainViewCliente vistaClietes;
	private MainViewTienda vistaTienda;
	private MainViewProveedor vistaProveedor;
	
	public static void main(String[] args) {
		
		// Populating elements
		tienda.setArticuloOrdenados(new ArrayList<>());
		tienda.getArticuloOrdenados().add(new ItemVenta(new Articulo("Zapallo", 500), 10));

		tienda.setArticulosEnStock(new ArrayList<>());
		tienda.getArticulosEnStock().add(new ItemVenta(new Articulo("Pure", 500), 20));
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitView window = new InitView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InitView() {
		initialize();
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
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton ingresoCliente = new JButton("Ingresar como Cliente");
		ingresoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.setVisible(false);
				if (vistaClietes==null)
					vistaClietes = new MainViewCliente(frame);
				else
					vistaClietes.getFrame().setVisible(true);
			}
		});
		GridBagConstraints gbc_ingresoCliente = new GridBagConstraints();
		gbc_ingresoCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_ingresoCliente.insets = new Insets(0, 0, 5, 5);
		gbc_ingresoCliente.gridx = 2;
		gbc_ingresoCliente.gridy = 1;
		frame.getContentPane().add(ingresoCliente, gbc_ingresoCliente);
		
		JButton ingresoTienda = new JButton("Ingresar como Tienda");
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
		
		JButton ingresoProveedor = new JButton("Ingresar como Proveedor");
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
	}

	
	
	public static Tienda getTienda() {
		return tienda;
	}

	public static void setTienda(Tienda tienda) {
		InitView.tienda = tienda;
	}
}