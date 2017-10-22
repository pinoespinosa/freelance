package utils;

import java.util.ArrayList;
import java.util.List;

public class LOG {

	private static boolean log = false;
	private static boolean reporte = true;

	private static List<String> historial = new ArrayList<>();

	
	
	public  static int cantidad_producto_duplicado = 0;

	public static int cantidad_descartado_palabra_clave = 0;
	public  static int cantidad_descartado_stock = 0;

	public static int cantidad_precios_actualizados = 0;
	
	public static int cantidad_producto_from_productobase = 0;
	public static int cantidad_producto_sugerido_matriz = 0;

	public static int cantidad_producto_user_creados = 0;
	public static int cantidad_producto_user_duplicados = 0;
	public static int cantidad_tipo_producto_user_creados = 0;
	public static int cantidad_tipo_producto_user_duplicados = 0;
	
	public static void log(String text) {

		if (log)
			System.out.println(text);

	}

	public static void text(String text){
		reporte(text);
	}
	
	
	
	
	public static void productoUserCreado(String text){
		reporte(text);
		cantidad_producto_user_creados++;
	}
	public static void tipoProductoUserCreado(String text){
		reporte(text);
		cantidad_tipo_producto_user_creados++;
	}
	
	public static void productoUserDuplicado(String text){
		reporte(text);
		cantidad_producto_user_duplicados++;
	}
	public static void tipoProductoUserDuplicado(String text){
		reporte(text);
		cantidad_tipo_producto_user_duplicados++;
	}
	
	
	
	public static void precioUpdate(String text){
		reporte(text);
		cantidad_precios_actualizados++;
	}

	public static void descartadoStock(String text){
		reporte(text);
		cantidad_descartado_stock++;
	}
	
	public static void productoDuplicado(String text){
		reporte(text);
		cantidad_producto_duplicado++;
	}
	
	public static void descartadoKey(String text){
		reporte(text);
		cantidad_descartado_palabra_clave++;
	}
	
	public static void creadoPorProductoBase(String text){
		reporte(text);
		cantidad_producto_from_productobase++;
	}
	
	public static void creadoPorProductoMatriz(String text){
		reporte(text);
		cantidad_producto_sugerido_matriz++;
	}
	
	private static void reporte(String text) {

		if (reporte){
			historial.add(text);
			System.out.println(text);
		}
	}

	public static List<String> getHistorialCSV() {
		
		List<String> ff = new ArrayList<>();
		ff.add("");
		ff.add("       REPORTE      ");
		ff.add("");
		ff.add("");
		ff.add("");
		ff.add("Cantidad de productos duplicados: " + cantidad_producto_duplicado);
		
		ff.add("Cantidad de productos stock: " + cantidad_descartado_stock);
		ff.add("Cantidad de productos descartados por palabras clave: " + cantidad_descartado_palabra_clave);
		
		ff.add("Cantidad de precios actualizados: " + cantidad_precios_actualizados);
		
		ff.add("Cantidad de productos creados a partir de prod base: " + cantidad_producto_from_productobase);
		ff.add("Cantidad de productos creados a partir matriz: " + cantidad_producto_sugerido_matriz);
		
		ff.add("");
		ff.add("");
		ff.add("");

		ff.addAll(historial);
		
		return ff;
	}

	public static List<String> getHistorialCreadosUser() {
		
		List<String> ff = new ArrayList<>();
		ff.add("");
		ff.add("       REPORTE      ");
		ff.add("");
		ff.add("");
		ff.add("");
		ff.add("Cantidad de productos creados: " + cantidad_producto_user_creados);
		ff.add("Cantidad de productos duplicados: " + cantidad_producto_user_duplicados);
		ff.add("");
		ff.add("Cantidad de tipo productos creados: " + cantidad_tipo_producto_user_creados);
		ff.add("Cantidad de tipo productos duplicados: " + cantidad_tipo_producto_user_duplicados);
		ff.add("");
		ff.add("");
		ff.add("");

		ff.addAll(historial);
		
		return ff;
	}
	
	public static void setHistorial(List<String> historial) {
		LOG.historial = historial;
	}
	
	public static void cleanHistorial() {
		LOG.historial.clear();
		cantidad_producto_duplicado=0;

		cantidad_descartado_stock=0;
		cantidad_descartado_palabra_clave = 0;

		cantidad_precios_actualizados = 0;
		
		cantidad_producto_from_productobase = 0;
		cantidad_producto_sugerido_matriz=0;
		
		cantidad_producto_user_creados = 0;
		cantidad_producto_user_duplicados = 0;
		cantidad_tipo_producto_user_creados = 0;
		cantidad_tipo_producto_user_duplicados = 0;
		
	}
	
}
