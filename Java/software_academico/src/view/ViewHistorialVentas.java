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

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import classes.ItemVenta;
import classes.Venta;
import javax.swing.JTextPane;

public class ViewHistorialVentas {

	private JFrame frame;
	private JFrame parent;
	JList<Venta> listStock_2;

	/**
	 * Create the application.
	 * 
	 * @param parent
	 */
	public ViewHistorialVentas(JFrame parent) {
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
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Vender Articulos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		listStock_2 = new JList<Venta>(new DefaultListModel<Venta>());
		GridBagConstraints gbc_listStock_2 = new GridBagConstraints();
		gbc_listStock_2.fill = GridBagConstraints.BOTH;
		gbc_listStock_2.gridwidth = 2;
		gbc_listStock_2.insets = new Insets(0, 0, 5, 5);
		gbc_listStock_2.gridx = 1;
		gbc_listStock_2.gridy = 0;
		panel_1.add(listStock_2, gbc_listStock_2);

		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 4;
		gbc_textPane.gridy = 0;
		panel_1.add(textPane, gbc_textPane);

		JButton btnEjecutarVenta = new JButton("Ver Detalle");
		GridBagConstraints gbc_btnEjecutarVenta = new GridBagConstraints();
		gbc_btnEjecutarVenta.insets = new Insets(0, 0, 5, 5);
		gbc_btnEjecutarVenta.gridx = 1;
		gbc_btnEjecutarVenta.gridy = 1;
		panel_1.add(btnEjecutarVenta, gbc_btnEjecutarVenta);
		
		JButton btnVolver = new JButton("Salir");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				frame.setVisible(false);
				parent.setVisible(true);
				
			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 0);
		gbc_btnVolver.gridx = 4;
		gbc_btnVolver.gridy = 1;
		panel_1.add(btnVolver, gbc_btnVolver);

		btnEjecutarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textPane.setText(listStock_2.getSelectedValue().toFullString());

				updateScreen();
				InitView.saveDB();
			}
		});

		updateScreen();

	}

	private void updateView(JList<Venta> list) {

		DefaultListModel<Venta> listVenta = (DefaultListModel<Venta>) list.getModel();

		listVenta.clear();
		for (Venta venta : InitView.getTienda().getVentas()) {
			listVenta.addElement(venta);
		}

	}

	private void updateScreen() {
		updateView(listStock_2);
		listStock_2.updateUI();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
