package data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

public class PdfPrinter {

	public static String printPDF(Acta acta, CuerpoColegiado ccOrig) {

		System.out.println("Metadata");

		String name = System.currentTimeMillis() + ".pdf";

		Document document = new Document(PageSize.LETTER);
		try {

			document.open();

			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);

			Font f2 = new Font();
			f2.setStyle(Font.BOLD);
			f2.setSize(8);
			f2.setColor(Color.WHITE);

			document.add(new Paragraph("Lugar: " + acta.getLugar() + " - " + acta.getCiudad(), f));
			document.add(new Paragraph("Fecha y hora: " + acta.getFechaReunion() + " / " + acta.getHoraInicio() + " a "
					+ acta.getHoraFinal(), f));
			document.add(new Paragraph("-", f2));
			document.add(new Paragraph("Fin en Mente: " + acta.getFinMenteGral(), f));

			document.add(new Paragraph("-", f2));
			document.add(new Paragraph("Integrantes", f));
			for (UsuarioActa integ : acta.getIntegrantes()) {
				document.add(new Paragraph(
						"  - " + integ.getNombre() + " (" + integ.getEmail() + ") " + integ.getEstado(), f));
			}

			for (Tema tema : ccOrig.getTemas().values()) {

				List<Evento> eventos = tema.getEventos();
				List<String> commentList = new ArrayList<String>();
				for (Evento evento : eventos) {
					if (evento.getTexto().contains(acta.getId()))
						commentList.add(evento.getTexto());
				}
				if (!commentList.isEmpty()) {
					document.add(new Paragraph("Tema: " + tema.getDetalle(), f));
					for (String comm : commentList) {
						document.add(new Paragraph("  - " + comm, f));
					}
					document.add(new Paragraph("-", f2));
				}

			}

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}

		document.close();

		return name;
	}

}