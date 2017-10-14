
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;

class OpenCvMagic {

	static JFrame frame;
	static JFrame frame_orig;

	static JLabel lbl;
	static ImageIcon icon;

	static JLabel lbl2;
	static ImageIcon icon2;

	//static String source = "http://75.130.56.53:80/mjpg/video.mjpg?COUNTER";
	static String source = "/home/pino/Downloads/example2.mp4";

	static BufferedImage imageOtra;
	static byte[] data;
	static int nByte = 3;

	static BufferedImage image;
	static BufferedImage fgMask;

	static BackgroundSubtractorMOG2 fgbg;
	// static BackgroundSubtractorMOG2 fgbg1 =
	// Video.createBackgroundSubtractorMOG2();
	static Mat mask;

	private final static double LEARNING_RATE = 0.01;


	public static Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}

	// Mat nesnesini image tipine dönü?tür
	public static BufferedImage ConvertMat2Image(Mat kameraVerisi, int ancho, int alto) {
		
		
		Size size = new Size(ancho,alto);//the dst image size,e.g.100x100
		Mat dst = new Mat();//dst image
		Mat src;//src image
		
		
		Imgproc.resize(kameraVerisi,dst,size);//resize image
		
		System.out.println("ancho" + ancho + " alto" + alto);

		MatOfByte byteMatVerisi = new MatOfByte();
		Imgcodecs.imencode(".jpg", dst, byteMatVerisi);
		byte[] byteArray = byteMatVerisi.toArray();
		BufferedImage goruntu = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			goruntu = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return goruntu;
	}

}
