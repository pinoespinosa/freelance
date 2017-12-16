package data;

import java.io.FileOutputStream;

import javax.swing.JFrame;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;

public class MainClass extends JFrame {

	public MainClass(CuerpoColegiado ccOrig, Tema tema) throws Exception {
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);

		
		
		
		
		
		Document document = new Document();
		// step 2
		PdfContentByte cb = PdfWriter.getInstance(document, new FileOutputStream("ejemplo.pdf")).getDirectContent();
		// step 3
		document.open();
		// step 4

	    PdfTemplate tp = cb.createTemplate(500, 500);
	    Graphics2D g2;
	    g2 = tp.createGraphicsShapes(500, 500);
	    // g2 = tp.createGraphics(500, 500);
	    getContentPane().print(g2);
	    g2.dispose();
	    cb.addTemplate(tp, 30, 300);
	    // step 5: we close the document
	    document.close();
	    
	    
		document.add(new Paragraph("Acta Numero :"));
		document.add(new Paragraph("Cuerpo Colegiado: " + ccOrig.getNombre() + " [" + ccOrig.getPrefijoDocs() + "/"
				+ ccOrig.getId() + "]"));
		document.add(new Paragraph(" "));

		document.add(new Paragraph());
		document.add(new Paragraph(tema.getDetalle()));
		document.add(new Paragraph(" "));

		for (Evento ev : tema.getEventos()) {
			document.add(new Paragraph(ev.getTexto()));

		}
		// step 5
		document.close();

	}
}