package data;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import datasource.DataSourceReal;

public class PdfPrinter {

	private static Paragraph EMPTY_LINE = null;

	public static String printPDF(Acta acta, CuerpoColegiado ccOrig) throws FileNotFoundException {

		Font f = new Font();
		f.setStyle(Font.BOLD);
		f.setSize(8);

		init();

		String name = System.currentTimeMillis() + ".pdf";

		Document document = new Document(PageSize.LETTER);
		try {

			PdfWriter.getInstance(document, new FileOutputStream(name));

			document.open();

			Paragraph parrafoTitulo = new Paragraph("ACTA " + acta.getNumeroActa().toUpperCase(), f);
			parrafoTitulo.setAlignment(Element.ALIGN_CENTER);
			document.add(parrafoTitulo);
			document.add(EMPTY_LINE);

			Paragraph parrafoCuerpo = new Paragraph(acta.getCuerpoColegiadoNombre().toUpperCase(), f);
			parrafoCuerpo.setAlignment(Element.ALIGN_CENTER);
			document.add(parrafoCuerpo);
			document.add(EMPTY_LINE);

			Paragraph parrafoFin = new Paragraph("Fin en Mente General: " + acta.getFinMenteGral().toUpperCase(), f);
			parrafoFin.setAlignment(Element.ALIGN_CENTER);
			document.add(parrafoFin);

			document.add(EMPTY_LINE);
			document.add(EMPTY_LINE);

			document.add(new Paragraph("FECHA " + acta.getFechaReunion() + " hs   DE " + acta.getHoraInicio() + " A "
					+ acta.getHoraFinal() + " hs", f));
			document.add(new Paragraph("LUGAR: " + acta.getLugar() + " CIUDAD " + acta.getCiudad(), f));

			document.add(EMPTY_LINE);
			document.add(EMPTY_LINE);

			document.add(new Paragraph("Integrantes:", f));
			for (UsuarioActa integ : acta.getIntegrantes()) {
				if (!integ.getEstado().equals("Ausente"))
					document.add(new Paragraph(
							integ.getNombre() + " (" + integ.getEstado() + ") Fin en Mente: " + integ.getFinEnMente(),
							f));
			}

			document.add(EMPTY_LINE);
			document.add(EMPTY_LINE);

			document.add(new Paragraph("Se trataron los siguientes temas con los siguientes comentarios:", f));
			document.add(EMPTY_LINE);

			document.add(new Paragraph("-------------------------------------------------------------", f));

			for (Tema tema : ccOrig.getTemas().values()) {

				boolean escribiAlgo = false;

				{

					List<Evento> eventos = tema.getEventos();
					List<String> commentList = new ArrayList<String>();

					for (Evento evento : eventos) {
						if (evento.getIdActa().contains(acta.getId()))
							commentList.add(evento.getTexto());
					}

					if (!commentList.isEmpty()) {
						escribiAlgo = true;
						document.add(new Paragraph("Tema: " + tema.getDetalle(), f));
						for (String comm : commentList) {
							if (comm.contains("/assets/"))
								document.add(new Paragraph("  - Se anexó el archivo " + comm.split("/assets/")[1], f));
							else
								document.add(new Paragraph("  - " + comm, f));
						}
						document.add(EMPTY_LINE);
					} else {
						if (tema.getEstado().equals(DataSourceReal.TEMA_CERRADO)) {
							document.add(new Paragraph("Tema: " + tema.getDetalle(), f));
							document.add(new Paragraph("  - Sin Comentarios", f));
							escribiAlgo = true;
						}
					}
				}

				if (escribiAlgo) {
					for (Tarea tarea : tema.getTareas()) {

						List<String> commentListTarea = new ArrayList<String>();

						for (String evento : tarea.getEventos()) {
							if (evento.contains(acta.getNumeroActa())) {
								commentListTarea.add(evento.split(acta.getNumeroActa().toUpperCase())[1]);
							}
						}
						if (!commentListTarea.isEmpty()) {
							escribiAlgo = true;
							document.add(new Paragraph("Tarea: " + tarea.getDetalle(), f));
							document.add(new Paragraph("   Responsable: " + tarea.getResponsable().getNombre(), f));

							for (String comm : commentListTarea) {
								document.add(new Paragraph("  " + comm, f));
							}
						} else {
							if (tarea.getEstado().equals(DataSourceReal.TEMA_CERRADO)) {
								escribiAlgo = true;
								document.add(new Paragraph("Tarea: " + tarea.getDetalle(), f));
								document.add(new Paragraph("  - Sin Comentarios", f));
							}
						}

					}

					document.add(new Paragraph("-------------------------------------------------------------", f));
				}
			}

			document.add(EMPTY_LINE);
			document.add(EMPTY_LINE);

			if (acta.getSeCumpliofinEnMente().equals("true")) {
				document.add(new Paragraph("Se cumplió el fin en mente de la reunión", f));
			} else {
				document.add(new Paragraph("No se cumplió el fin en mente de la reunión", f));
			}

			if (acta.getElTiempoFueSuficiente().equals("true")) {
				document.add(new Paragraph("Fue suficiente el tiempo utilizado en la reunión", f));
			} else {
				document.add(new Paragraph("No fue suficiente el tiempo utilizado en la reunión", f));
			}

			if (acta.getHuboInconvenientes().equals("true")) {
				document.add(new Paragraph("La reunión tuvo estos inconvenientes:", f));
				document.add(new Paragraph(acta.getHuboInconvenientesTexto(), f));
			} else {
				document.add(new Paragraph("La reunión finalizó sin inconvenientes", f));
			}

			List<UsuarioActa> actaAusente = new ArrayList<>();
			for (UsuarioActa integ : acta.getIntegrantes()) {
				if (integ.getEstado().equals("Ausente"))
					actaAusente.add(integ);
			}

			if (!actaAusente.isEmpty()) {
				document.add(new Paragraph("Estuvieron ausentes los siguientes miembros", f));
				for (UsuarioActa usuarioActa : actaAusente) {
					document.add(new Paragraph(usuarioActa.getNombre(), f));
				}
			}

			if (acta.getTieneSugerencias().equals("true")) {
				document.add(new Paragraph("Se hizo la siguiente sugerencia a la reunión:", f));
				document.add(new Paragraph(acta.getTieneSugerenciasTexto(), f));
			}

			document.add(EMPTY_LINE);
			document.add(new Paragraph("La redacción de las tareas nuevas fue aprobada por los responsables.", f));
			document.add(EMPTY_LINE);
			document.add(new Paragraph(
					"Esta acta se genera en forma automática por ser usada dentro de una metodología de reuniones, y fue aprobada al finalizar la reunión",
					f));

			document.add(EMPTY_LINE);

			if (!acta.getComentarioAdicionales().isEmpty()) {
				document.add(new Paragraph("Comentarios Adicionales:", f));
				document.add(new Paragraph(acta.getComentarioAdicionales(), f));
				document.add(EMPTY_LINE);
			}

			document.add(new Paragraph("Moderador de la reunión " + acta.getIntegranteAutorActa(), f));

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