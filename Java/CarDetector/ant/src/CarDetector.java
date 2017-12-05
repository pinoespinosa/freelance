
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.videoio.VideoCapture;












class CarDetector {

	// static JFrame frame;
	// static JFrame frame_orig;

	// static JLabel lbl;
	static ImageIcon icon;

	static JLabel lbl2;
	static ImageIcon icon2;

	// static String source = "http://75.130.56.53:80/mjpg/video.mjpg?COUNTER";
	static String source = "example4.mp4";

	static BufferedImage imageOtra;
	static byte[] data;
	static int nByte = 3;

	static BufferedImage image;
	static BufferedImage fgMask;

	static BackgroundSubtractorMOG2 fgbg;

	static Mat mask;

	private final static double LEARNING_RATE = 0.01;

	static int anchoVentana = 1;
	static int altoVentana = 1;

	private static int tamLocAncho = 1;
	private static int tamLocAlto = 1;

	Mat getMask(Mat mat) {
		fgbg.apply(mat, mask);
		return mask;
	}

	public static void showCars(JLabel marcoImg, JFrame frame2, JTextPane texto) throws Exception {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		mask = new Mat();

		System.out.println("pas");

		CascadeClassifier cascadeEyeClassifier = new CascadeClassifier(
				"cars.xml");

		VideoCapture videoDevice = new VideoCapture(source);
		// videoDevice.open(source);

		Mat frameN2_COPIA = new Mat();
		if (videoDevice.isOpened()) {

			Mat frInicial = new Mat();

			videoDevice.read(frInicial);

			Mat base = Imgcodecs.imread("fondo.bmp");

			int cantidad = 0;
			float valores = 0;

			Mat frameCapture = new Mat();
			videoDevice.read(frameCapture);

			Mat frame_ANTIGUO = frameCapture.clone();
			Mat frame_ANTIGUO2 = frameCapture.clone();
			Mat frame_ANTIGUO3 = frameCapture.clone();
			Mat frame_ANTIGUO4 = frameCapture.clone();
			Mat frame_ANTIGUO5 = frameCapture.clone();
			Mat frame_ANTIGUO6 = frameCapture.clone();
			Mat frame_ANTIGUO7 = frameCapture.clone();
			Mat frame_ANTIGUO8 = frameCapture.clone();
			Mat frame_ANTIGUO9 = frameCapture.clone();
			Mat frame_ANTIGUO10 = frameCapture.clone();
			Mat frame_ANTIGUO11 = frameCapture.clone();
			Mat frame_ANTIGUO12 = frameCapture.clone();
			Mat frame_ANTIGUO13 = frameCapture.clone();
			Mat frame_ANTIGUO14 = frameCapture.clone();

			while (true) {
				frame_ANTIGUO14 = frame_ANTIGUO13;
				frame_ANTIGUO13 = frame_ANTIGUO12;
				frame_ANTIGUO12 = frame_ANTIGUO11;
				frame_ANTIGUO11 = frame_ANTIGUO10;
				frame_ANTIGUO10 = frame_ANTIGUO9;
				frame_ANTIGUO9 = frame_ANTIGUO8;
				frame_ANTIGUO8 = frame_ANTIGUO7;
				frame_ANTIGUO7 = frame_ANTIGUO6;
				frame_ANTIGUO6 = frame_ANTIGUO5;
				frame_ANTIGUO5 = frame_ANTIGUO4;
				frame_ANTIGUO4 = frame_ANTIGUO3;
				frame_ANTIGUO3 = frame_ANTIGUO2;
				frame_ANTIGUO2 = frame_ANTIGUO;
				frame_ANTIGUO = frameCapture.clone();

				Imgproc.blur(frame_ANTIGUO, frame_ANTIGUO, new Size(5, 5));

				videoDevice.read(frameCapture);

				Mat frameCaptureBorders2 = frameCapture.clone();

				Core.subtract(frameCapture, base, frameCaptureBorders2);

				Mat resta1 = new Mat();
				Core.subtract(frameCaptureBorders2, frame_ANTIGUO, resta1);
				Core.bitwise_not(resta1, resta1);
				Core.subtract(frameCapture, resta1, resta1);

				Mat resta2 = new Mat();
				Core.subtract(frameCaptureBorders2, frame_ANTIGUO5, resta2);
				Core.bitwise_not(resta2, resta2);
				Core.subtract(frameCapture, resta2, resta2);

				Mat resta3 = new Mat();
				Core.subtract(frameCaptureBorders2, frame_ANTIGUO9, resta3);
				Core.bitwise_not(resta3, resta3);
				Core.subtract(frameCapture, resta3, resta3);

				Mat resta4 = new Mat();
				Core.subtract(frameCaptureBorders2, frame_ANTIGUO14, resta4);
				Core.bitwise_not(resta4, resta4);
				Core.subtract(frameCapture, resta4, resta4);

				Core.add(resta1, resta2, frameCaptureBorders2);
				Core.add(resta3, frameCaptureBorders2, frameCaptureBorders2);
				Core.add(resta4, frameCaptureBorders2, frameCaptureBorders2);

				Imgproc.cvtColor(frameCaptureBorders2, frameCaptureBorders2, Imgproc.COLOR_RGB2GRAY);

				MatOfRect eyes = new MatOfRect();
				cascadeEyeClassifier.detectMultiScale(frameCapture, eyes);

				for (Rect rect : eyes.toArray()) {

					Mat forma = frameCaptureBorders2.clone();

					Rect rectCrop = new Rect(new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height));
					Mat image_output = forma.submat(rectCrop);

					double color = Core.sumElems(image_output).val[0] / rect.area();

					if (true) {
						Imgproc.putText(frame_ANTIGUO, "Auto", new Point(rect.x, rect.y - 5), 1, 2,
								new Scalar(0, 0, 255)); // Kare
						Imgproc.rectangle(frame_ANTIGUO, new Point(rect.x, rect.y),
								new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(200, 200, 100), 2);

						if (cantidad > 10) {
							texto.setText(new Float(valores / 10).intValue() + " vehiculos detectados");
							cantidad = 0;
							valores = 0;



							   URL url = new URL("http://localhost:8080/car-counter/api/car/moving");
						        HttpURLConnection con = (HttpURLConnection) url.openConnection();
						        con.setRequestMethod("POST");
						        con.setRequestProperty("Content-Type", "application/json");

						        Map<String, String> parameters = new HashMap<>();
						        parameters.put("param1", "val");
						        con.setDoOutput(true);
						        DataOutputStream out = new DataOutputStream(con.getOutputStream());
						        out.flush();
						        out.close();

						        int status = con.getResponseCode();
						        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						        String inputLine;
						        StringBuilder content = new StringBuilder();
						        while ((inputLine = in.readLine()) != null) {
						            content.append(inputLine);
						        }
						        in.close();







						} else
							valores += 1;

					}

				}

				cantidad++;

				boolean valor = (anchoVentana == frame2.getSize().width) && (altoVentana == frame2.getSize().height);

				if (!valor) {
					anchoVentana = frame2.getSize().width;
					altoVentana = frame2.getSize().height;

					tamLocAlto = marcoImg.getSize().height;
					tamLocAncho = marcoImg.getSize().width;

		//			System.out.println("cambie" + tamLocAlto + " " + tamLocAncho);
				}
				else
		//			System.out.println("igual" + tamLocAlto + " " + tamLocAncho);
					

				PushImage(OpenCvMagic.ConvertMat2Image(frame_ANTIGUO, tamLocAncho, tamLocAlto), marcoImg,
						frame2);

			}
		} else {
			System.out.println("No se pudieron obtener imagenes");
			return;
		}

	}

	public static void PushImage(Image img2, JLabel lbl, JFrame frame) {

		// lbl.setSize(ancho, alto);
		icon = new ImageIcon(img2);
		lbl.setIcon(icon);
		lbl.update(lbl.getGraphics());
		frame.revalidate();

	}

}
