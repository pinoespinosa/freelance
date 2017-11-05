package datasource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

	public final static String end_line = "##finLinea##";
	public final static String sep_column = "##123##";

	public static List<String> readCsvFile(String filename) {

		String line = "";
		String total = "";

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			while ((line = br.readLine()) != null) {
				total += line + end_line + ",";
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}

		return procesar(total);

	}

	private static List<String> procesar(String texto) {

		// Reemplazo las comillas dobles por 2 simples
		texto = texto.replace("\"\"", "''");

		List<String> normal = new ArrayList<>();

		boolean comillaAbierta = false;

		String fila = "";

		for (String string : texto.split(",")) {

			if (string.startsWith("\""))
				comillaAbierta = true;

			if (string.endsWith("\"") || string.endsWith("\"" + end_line))
				comillaAbierta = false;

			if (comillaAbierta) {
				fila += string.replaceAll("\"", "");
			} else {
				if (string.endsWith(end_line)) {
					normal.add(fila + string.replace(end_line, "").replaceAll("\"", ""));
					fila = "";
				} else
					fila += string.replaceAll("\"", "") + sep_column;
			}
		}

		// Elimino el header
		normal.remove(0);

		return normal;

	}

	public static void write(List<String> productos, String name) {

		PrintWriter writer;
		try {
			writer = new PrintWriter(name, "UTF-8");

			for (String line : productos) {
				writer.println(line);
			}
			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}