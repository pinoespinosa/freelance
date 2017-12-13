package data;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainClass extends JFrame {

 

  public MainClass(CuerpoColegiado ccOrig, Tema tema) throws Exception {

      Document document = new Document();
      // step 2
      PdfWriter.getInstance(document, new FileOutputStream("ejemplo.pdf"));
      // step 3
      document.open();
      // step 4

		document.add(new Paragraph("Acta Numero :"));
		document.add(new Paragraph("Cuerpo Colegiado: " + ccOrig.getNombre() + " [" + ccOrig.getPrefijoDocs() + "/"
				+ ccOrig.getId() + "]"));
		document.add(new Paragraph(" "));

      document.add(new Paragraph(  ));
      document.add(new Paragraph( tema.getDetalle() ));
      document.add(new Paragraph(" "));
      
      for (Evento ev : tema.getEventos()) {
          document.add(new Paragraph( ev.getTexto() ));

	}
      // step 5
      document.close();
	  
	  
  }
}