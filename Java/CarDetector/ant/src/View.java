import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

public class View {

	private JFrame frame;
	private JLabel imagen;
	private JComboBox calleComboBox;
	private JComboBox camaraComboBox;
	private JComboBox semaforoComboBox;
	private JTextPane txtpnInformacionInformacion;
	private JLabel lblAreaDeDeteccion;
	private JLabel lblSegundoSemforo;
	private JLabel lblNewLabel_1;
	private JLabel lblIntermitenteTiempoIndefinido;
	private JLabel lblVerdeIndefinido;
	private JLabel lblRojoIndefinido;
	private JLabel lblPasarATiempos;
	private JTextPane txtVehiculos;
	private JLabel lblEstadisticas;
	private JButton startButton;

	boolean cambio = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Thread thread = new Thread(new Runnable() {

						public void run() {
							CarDetector cam = new CarDetector();
							try {
								cam.showCars(null, null, null, "http://75.130.56.53/mjpg/video.mjpg?COUNTER","1", "FONDO_COUNTER.bmp");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					thread.start();

					Thread thread2 = new Thread(new Runnable() {

						public void run() {
							CarDetector cam = new CarDetector();
							try {
								cam.showCars(null, null, null, "http://104.157.73.60:80/cgi-bin/faststream.jpg?stream=half&fps=15&rand=COUNTER","2", "EJEMPLO6.bmp");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					thread2.start();
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				/*
				 * System.out.println("Tamañoo");
				 * 
				 * int ancho = new
				 * Double(frame.getSize().width*0.75).intValue(); int alto = new
				 * Double(frame.getSize().height*0.75).intValue();
				 * imagen.setSize(ancho, alto);
				 */
			}
		});
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setSize(800, 600);
		frame.setBounds(100, 100, 800, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 25, 0, 313, 0, 25, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 0, 290, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		calleComboBox = new JComboBox();
		calleComboBox.setModel(new DefaultComboBoxModel(new String[] { "San Lorenzo y Belgrano" }));
		GridBagConstraints gbc_calleComboBox = new GridBagConstraints();
		gbc_calleComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_calleComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_calleComboBox.gridx = 1;
		gbc_calleComboBox.gridy = 1;
		frame.getContentPane().add(calleComboBox, gbc_calleComboBox);

		camaraComboBox = new JComboBox();
		camaraComboBox.setModel(new DefaultComboBoxModel(new String[] { "Camara 1", "Camara 2" }));
		GridBagConstraints gbc_camaraComboBox = new GridBagConstraints();
		gbc_camaraComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_camaraComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_camaraComboBox.gridx = 2;
		gbc_camaraComboBox.gridy = 1;
		frame.getContentPane().add(camaraComboBox, gbc_camaraComboBox);

		semaforoComboBox = new JComboBox();
		semaforoComboBox.setModel(new DefaultComboBoxModel(new String[] { "Semaforo 1" }));
		GridBagConstraints gbc_semaforoComboBox = new GridBagConstraints();
		gbc_semaforoComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_semaforoComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_semaforoComboBox.gridx = 3;
		gbc_semaforoComboBox.gridy = 1;
		frame.getContentPane().add(semaforoComboBox, gbc_semaforoComboBox);

		imagen = new JLabel("");
		imagen.setIcon(null);

		imagen.setOpaque(true);
		imagen.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setBackground(Color.BLACK);
		GridBagConstraints gbc_imagen = new GridBagConstraints();
		gbc_imagen.gridheight = 5;
		gbc_imagen.insets = new Insets(0, 0, 5, 5);
		gbc_imagen.gridwidth = 2;
		gbc_imagen.fill = GridBagConstraints.BOTH;
		gbc_imagen.gridx = 1;
		gbc_imagen.gridy = 2;
		frame.getContentPane().add(imagen, gbc_imagen);

		txtpnInformacionInformacion = new JTextPane();
		txtpnInformacionInformacion.setEditable(false);
		txtpnInformacionInformacion
				.setText("Informacion 1\nInformacion 2\nInformacion 3\nInformacion 4\nInformacion 5");
		GridBagConstraints gbc_txtpnInformacionInformacion = new GridBagConstraints();
		gbc_txtpnInformacionInformacion.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnInformacionInformacion.fill = GridBagConstraints.BOTH;
		gbc_txtpnInformacionInformacion.gridx = 3;
		gbc_txtpnInformacionInformacion.gridy = 2;
		frame.getContentPane().add(txtpnInformacionInformacion, gbc_txtpnInformacionInformacion);

		lblAreaDeDeteccion = new JLabel("Area de deteccion");
		lblAreaDeDeteccion.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAreaDeDeteccion = new GridBagConstraints();
		gbc_lblAreaDeDeteccion.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAreaDeDeteccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAreaDeDeteccion.gridx = 3;
		gbc_lblAreaDeDeteccion.gridy = 3;
		frame.getContentPane().add(lblAreaDeDeteccion, gbc_lblAreaDeDeteccion);

		lblSegundoSemforo = new JLabel("Segundo Semáforo");
		GridBagConstraints gbc_lblSegundoSemforo = new GridBagConstraints();
		gbc_lblSegundoSemforo.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSegundoSemforo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSegundoSemforo.gridx = 3;
		gbc_lblSegundoSemforo.gridy = 4;
		frame.getContentPane().add(lblSegundoSemforo, gbc_lblSegundoSemforo);

		lblNewLabel_1 = new JLabel("Horarios Intermitente");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 5;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		lblIntermitenteTiempoIndefinido = new JLabel("Intermitente Tiempo Indefinido");
		GridBagConstraints gbc_lblIntermitenteTiempoIndefinido = new GridBagConstraints();
		gbc_lblIntermitenteTiempoIndefinido.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIntermitenteTiempoIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntermitenteTiempoIndefinido.gridx = 3;
		gbc_lblIntermitenteTiempoIndefinido.gridy = 6;
		frame.getContentPane().add(lblIntermitenteTiempoIndefinido, gbc_lblIntermitenteTiempoIndefinido);

		startButton = new JButton("Start");
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				ExecutorService executor = Executors.newSingleThreadExecutor();
				executor.submit(() -> {

				});

				startButton.setEnabled(false);

			}
		});
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_startButton.anchor = GridBagConstraints.NORTH;
		gbc_startButton.gridwidth = 2;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 7;
		frame.getContentPane().add(startButton, gbc_startButton);

		txtVehiculos = new JTextPane();
		GridBagConstraints gbc_txtVehiculos = new GridBagConstraints();
		gbc_txtVehiculos.gridheight = 3;
		gbc_txtVehiculos.gridwidth = 2;
		gbc_txtVehiculos.insets = new Insets(0, 0, 5, 5);
		gbc_txtVehiculos.fill = GridBagConstraints.BOTH;
		gbc_txtVehiculos.gridx = 1;
		gbc_txtVehiculos.gridy = 8;
		frame.getContentPane().add(txtVehiculos, gbc_txtVehiculos);

		lblVerdeIndefinido = new JLabel("Verde Indefinido");
		GridBagConstraints gbc_lblVerdeIndefinido = new GridBagConstraints();
		gbc_lblVerdeIndefinido.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblVerdeIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerdeIndefinido.gridx = 3;
		gbc_lblVerdeIndefinido.gridy = 7;
		frame.getContentPane().add(lblVerdeIndefinido, gbc_lblVerdeIndefinido);

		lblRojoIndefinido = new JLabel("Rojo Indefinido");
		GridBagConstraints gbc_lblRojoIndefinido = new GridBagConstraints();
		gbc_lblRojoIndefinido.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblRojoIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_lblRojoIndefinido.gridx = 3;
		gbc_lblRojoIndefinido.gridy = 8;
		frame.getContentPane().add(lblRojoIndefinido, gbc_lblRojoIndefinido);

		lblPasarATiempos = new JLabel("Pasar a tiempos de controladora");
		GridBagConstraints gbc_lblPasarATiempos = new GridBagConstraints();
		gbc_lblPasarATiempos.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasarATiempos.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasarATiempos.gridx = 3;
		gbc_lblPasarATiempos.gridy = 9;
		frame.getContentPane().add(lblPasarATiempos, gbc_lblPasarATiempos);

		lblEstadisticas = new JLabel("Estadisticas");
		GridBagConstraints gbc_lblEstadisticas = new GridBagConstraints();
		gbc_lblEstadisticas.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEstadisticas.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstadisticas.gridx = 3;
		gbc_lblEstadisticas.gridy = 10;
		frame.getContentPane().add(lblEstadisticas, gbc_lblEstadisticas);
	}

}
