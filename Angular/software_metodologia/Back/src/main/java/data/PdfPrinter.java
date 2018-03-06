package data;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class PdfPrinter {

	private static Paragraph EMPTY_LINE = null;
	

	
	public static String printPDF(Acta acta, CuerpoColegiado ccOrig) throws FileNotFoundException {

		Font f = new Font();
		f.setStyle(Font.BOLD);
		f.setSize(8);

		init();

		
		System.out.println("Metadata");

		String name = System.currentTimeMillis() + ".pdf";

		Document document = new Document(PageSize.LETTER);
		try {

			PdfWriter.getInstance(document, new FileOutputStream(name));
			
			document.open();

			document.add(new Paragraph("ACTA " + acta.getNumeroActa().toUpperCase(), f));
			document.add(new Paragraph(acta.getCuerpoColegiadoNombre().toUpperCase(), f));
			document.add(new Paragraph("Fin en Mente General: " + acta.getFinMenteGral().toUpperCase(), f));

			document.add(EMPTY_LINE);

			document.add(new Paragraph("FECHA " + acta.getFechaReunion() + "   DE " + acta.getHoraInicio() + " A "
					+ acta.getHoraFinal(), f));
			document.add(new Paragraph("LUGAR: " + acta.getLugar() + " CIUDAD " + acta.getCiudad(), f));
			
			document.add(EMPTY_LINE);	
			document.add(EMPTY_LINE);
			
			document.add(new Paragraph("Integrantes:", f));
			for (UsuarioActa integ : acta.getIntegrantes()) {
				document.add(new Paragraph(integ.getNombre() + " (" + integ.getEstado() + ") Fin en Mente: " + integ.getFinEnMente(), f));
			}

			document.add(EMPTY_LINE);	
			document.add(EMPTY_LINE);

			
			document.add(new Paragraph("Se trataron los siguientes temas con los siguientes comentarios:", f));

			
			for (Tema tema : ccOrig.getTemas().values()) {

				List<Evento> eventos = tema.getEventos();
				List<String> commentList = new ArrayList<String>();
				
				for (Evento evento : eventos) {
					if (evento.getIdActa().contains(acta.getId()))
						commentList.add(evento.getTexto());
				}
				
				if (!commentList.isEmpty()) {
					document.add(new Paragraph("Tema: " + tema.getDetalle(), f));
					for (String comm : commentList) {
						document.add(new Paragraph("  - " + comm, f));
					}
					document.add(EMPTY_LINE);
				}

			}

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}

		document.close();

		return name;
	}

	private static void init() {

		Font f2 = new Font(Font.BOLD, 8);
		f2.setStyle(Font.BOLD);
		f2.setSize(8);
		f2.setColor(Color.WHITE);

		if (EMPTY_LINE == null) {
			EMPTY_LINE = new Paragraph("-", f2);
		}

	}
	
}