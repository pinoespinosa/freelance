package csvMarket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import tiendas.ProductoAbs;
import tiendas.TipoProducto;
import utils.BD;
import utils.CSVUtils;
import utils.LOG;

public class mainProcessor {

	static List<ProductoAbs> rechazados = new ArrayList<>();

	static List<ProductoAbs> rechazadosProcesados = new ArrayList<>();

	private static Hashtable<String, CategoriaInfo> categoriasPorPalabra = new Hashtable<>();
	private static Hashtable<String, CategoriaInfo> categoriasPorCat1Cat2Cat3 = new Hashtable<>();
	
	public static void main(String[] args) throws SQLException {

		LOG.log("Starting");
		LOG.log("");
		cargarMatrizCategorias();
//		rechazadosProcesados.addAll(processCSV("carrefour_output.csv"));	
		crearProductosValidadosPorUsuario("archivosNuevos.csv");
		
		LOG.log("Fin del proceso");
		BD.closeConnection();
	}

	private static void crearProductosValidadosPorUsuario(String filename) throws SQLException {
		
		List<TipoProducto> listaProd = new ArrayList<>();
					
		LOG.log("Leyendo datos CSV ...");
		for (String line : CSVUtils.readCsvFile(filename)) {
			if (!line.isEmpty() ) {
				ProductoAbs prod = ProductoAbs.create(line);
				if (!listaProd.contains(prod)){
					
					String keyCat = prod.getCategoria_1() + '_' + prod.getCategoria_2() + '_' + prod.getCategoria_3();
					CategoriaInfo cat = categoriasPorCat1Cat2Cat3.get(keyCat);
					
					if (cat!=null)
					{
						prod.setUrl_imagen_1(prod.editUrl(prod.getUrl_imagen_1()));
						prod.setUrl_imagen_2(prod.editUrl(prod.getUrl_imagen_2()));
						prod.setUrl_imagen_3(prod.editUrl(prod.getUrl_imagen_3()));
						prod.setUrl_imagen_4(prod.editUrl(prod.getUrl_imagen_4()));
						prod.setUrl_imagen_5(prod.editUrl(prod.getUrl_imagen_5()));

						TipoProducto tip = new TipoProducto(prod,cat);
						listaProd.add(tip);
						System.out.println(Script.createTipoProduct(tip));
					}
					
				}
			}
		}
				
		
	}

	private static List<ProductoAbs> processCSV(String fileName) throws SQLException{
		LOG.log("Procesando " + fileName);
		
		List<ProductoAbs> listaProd = new ArrayList<>();
					
		LOG.log("Leyendo datos CSV ...");
		for (String line : CSVUtils.readCsvFile(fileName)) {
			if (!line.isEmpty() ) {
				ProductoAbs prod = ProductoAbs.create(line);
				if (!listaProd.contains(prod))
					listaProd.add(prod);
			}
		}

		if (!listaProd.isEmpty()){
			desactivarProductos(listaProd.get(0).getTiendaID());
			listaProd = eliminarProductosPorStock(listaProd);
			listaProd = eliminarProductosPorPalabrasClave(listaProd);
			listaProd = updatePrices(listaProd);
		
		}
		List<ProductoAbs> noCreados = ProductFactory.crearProductosTiendaFromProductos(rechazados);
		
		ProductFactory.crearProductosTiendaFromMatriz(noCreados, categoriasPorPalabra);
		
		LOG.log("Agregando productos no creados ...");
		LOG.log("Finalido " + fileName);
		LOG.log("");
		return noCreados;
	}
	
	private static void cargarMatrizCategorias() {
		for (String line : CSVUtils.readCsvFile("Matriz.csv")) {
			if (!line.replace(CSVUtils.sep_column, "").isEmpty()) {
				CategoriaInfo cat = new CategoriaInfo(line);
				categoriasPorPalabra.put(cat.getPalabra(), cat);
				categoriasPorCat1Cat2Cat3.put(cat.getCatKey(), cat);
			}
		}
	}
	
	private static List<ProductoAbs> updatePrices(List<ProductoAbs> productos) throws SQLException {
		LOG.log("Actualizando precios y urls ...");
		LOG.reporte(" ------------------ ACTUALIZACION DE PRECIOS  ------------------ ");
		List<ProductoAbs> filtrados = new ArrayList<>();

		for (ProductoAbs dato : productos) {

			if (dato.getId() != null && !dato.getId().isEmpty() && dato.getTienda() != null
					&& !dato.getTienda().isEmpty() && dato.getValor() != null && !dato.getValor().isEmpty())
			{
				String exist = BD.executeSqlQueryString(Script.existProduct(dato.getId()), "existe");

				if (!exist.equals("0")) {
					BD.executeSqlVoid(Script.updateProduct(dato.getValorNumero(), dato.getUrl_redireccion(), dato.getId(), dato.getTiendaID()));
					filtrados.add(dato);
					LOG.reporte( dato.getTitulo() + " se establecio el precio de ------>" + dato.getValorNumero());
				} else {
					rechazados.add(dato);
				}
			}

		}
		LOG.reporte("");
		return filtrados;
	}

	private static List<ProductoAbs> eliminarProductosPorPalabrasClave(List<ProductoAbs> productos) {
		LOG.log("Eliminando datos por palabras ...");

		String line = "";

		List<String> palabras = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("ProductosQueNoVan.txt"))) {

			while ((line = br.readLine()) != null) {
				palabras.add(line);
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}

		LOG.reporte(" --------------- DESCARTADOS POR PALABRAS CLAVE --------------- ");

		List<ProductoAbs> filtrados = new ArrayList<>();
		for (ProductoAbs datoTienda : productos) {

			boolean ignorado = false;

			for (String palabra : palabras) {

				if (datoTienda.getTitulo().toLowerCase().contains(palabra.toLowerCase())){
					ignorado = true;
					LOG.reporte(datoTienda.getTitulo() + " ------> "+ palabra.toLowerCase());

				}
			}

			if (!ignorado)
				filtrados.add(datoTienda);

		}
		LOG.reporte("");
		return filtrados;
	}

	private static List<ProductoAbs> eliminarProductosPorStock(List<ProductoAbs> productos) {
		LOG.log("Eliminando datos por Stock ...");

		List<ProductoAbs> filtrados = new ArrayList<>();
		for (ProductoAbs datoTienda : productos) {
			if (!"Agotado".equals(datoTienda.getStock()) && !"Sin stock".equals(datoTienda.getStock())
					&& !"Consultar".equals(datoTienda.getStock()))
				filtrados.add(datoTienda);
		}
		return filtrados;
	}

	private static void desactivarProductos(String tienda) throws SQLException {
		LOG.log("Desactivando productos ...");
		BD.executeSqlVoid("UPDATE dureka.productos SET estado = '0' WHERE tienda = '" + tienda + "';");
	}

}
