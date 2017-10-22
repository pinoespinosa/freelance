package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import csvMarket.CategoriaInfo;

public class ConfigUtils {

	// Categorias
	public static Hashtable<String, CategoriaInfo> categoriasPorPalabra = new Hashtable<>();
	private static Hashtable<String, CategoriaInfo> categoriasPorCat1Cat2Cat3 = new Hashtable<>();

	// Config Data
	private static Hashtable<String, String> configDatos = new Hashtable<>();

	private static List<String> excludeWord = new ArrayList<>();

	public static Hashtable<String, CategoriaInfo> getCategoriasPorPalabra() {
		return categoriasPorPalabra;
	}

	public static void setCategoriasPorPalabra(Hashtable<String, CategoriaInfo> categoriasPorPalabra) {
		ConfigUtils.categoriasPorPalabra = categoriasPorPalabra;
	}

	public static Hashtable<String, CategoriaInfo> getCategoriasPorCat1Cat2Cat3() {
		return categoriasPorCat1Cat2Cat3;
	}

	public static void setCategoriasPorCat1Cat2Cat3(Hashtable<String, CategoriaInfo> categoriasPorCat1Cat2Cat3) {
		ConfigUtils.categoriasPorCat1Cat2Cat3 = categoriasPorCat1Cat2Cat3;
	}

	public static Hashtable<String, String> getConfigDatos() {
		return configDatos;
	}

	public static void setConfigDatos(Hashtable<String, String> configDatos) {
		ConfigUtils.configDatos = configDatos;
	}

	public static List<String> getExcludeWord() {
		return excludeWord;
	}

	public static void setExcludeWord(List<String> excludeWord) {
		ConfigUtils.excludeWord = excludeWord;
	}

	private static void cargarMatrizCategorias() {
		if (categoriasPorPalabra.isEmpty()) {
			for (String line : CSVUtils.readCsvFile("Matriz.csv")) {
				if (!line.replace(CSVUtils.sep_column, "").isEmpty()) {
					CategoriaInfo cat = new CategoriaInfo(line);
					categoriasPorPalabra.put(cat.getPalabra(), cat);
					categoriasPorCat1Cat2Cat3.put(cat.getCatKey(), cat);
				}
			}
		}
	}

	private static void cargarArchivoDatos() {

		for (String line : CSVUtils.readCsvFile("config.csv")) {
			if (!line.replace(CSVUtils.sep_column, "").isEmpty()) {
				configDatos.put(line.split(CSVUtils.sep_column)[0], line.split(CSVUtils.sep_column)[1]);
			}
		}
	}

	private static void cargarPalabrasExcluidas() {

		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader("ProductosQueNoVan.txt"))) {

			while ((line = br.readLine()) != null) {
				excludeWord.add(line);
			}

		} catch (FileNotFoundException e) {
			String path = "";
			try {
				path = new File(".").getCanonicalPath();
			} catch (IOException e1) {
			}
			throw new RuntimeException("No se pudo hallar el archivo ProductosQueNoVan.txt en el directorio " + path);

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	public  static void initialize() {

		if (!isInitialice()) {
			cargarArchivoDatos();
			cargarMatrizCategorias();
			cargarPalabrasExcluidas();
		}
	}

	private static boolean isInitialice(){
		return !getConfigDatos().isEmpty();
	}
	
}
