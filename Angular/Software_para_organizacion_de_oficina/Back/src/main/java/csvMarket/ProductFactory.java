package csvMarket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import tiendas.ProductoAbs;
import utils.BD;
import utils.CSVUtils;
import utils.ConfigUtils;
import utils.LOG;

public class ProductFactory {

	
	public static List<ProductoAbs> crearProductosTiendaFromProductos(List<ProductoAbs> productos) throws SQLException {
		LOG.log("Creando productos ...");
		LOG.text(" ------- CREACION DE PRODUCTOS TIENDA A PARTIR DE PRODUCTOS -------- ");

		List<ProductoAbs> filtrados = new ArrayList<>();

		
		for (ProductoAbs data : productos) {

			String existe = BD.executeSqlQueryString(Script.existTipoProductLike(data.getTitulo().replaceAll("'", "")), "cant");
								
			if (!existe.equals("0")){
				String texto = BD.executeSqlQueryString(Script.getDataTipoProduct(data.getTitulo().replaceAll("'", "")),"info");
				
				String script = Script.createProduct(texto.split("_")[0], texto.split("_")[1], texto.split("_")[2],
						texto.split("_")[3], texto.split("_")[4], data);
				BD.executeSqlVoid(script);
				
			}
			else
			{
				filtrados.add(data);
			}
			
		}
		
		return filtrados;
	}

	/**
	 * A partir de la BD + el CSV genera un archivo CSV para que el usuario valide y cree los nuevos productos
	 * @param productos
	 * @param response
	 * @throws IOException
	 */
	public static void crearProductosTiendaFromMatriz(List<ProductoAbs> productos, HttpServletResponse response) throws IOException {
		LOG.text("");
		LOG.log("Creando productos desde Matriz...");
		LOG.text("(Pendientes de aprobacion del usuario)");
		LOG.text(" ------- CREACION DE PRODUCTOS TIENDA A PARTIR DE MATRIZ -------- ");
		
		Hashtable<String,ProductoAbs> filtrados = new Hashtable<>();

		for (ProductoAbs prod : productos) {
			boolean done = false;
			for (String key : ConfigUtils.categoriasPorPalabra.keySet()) {
				if (!done && prod.getTitulo().contains(key)) {

					CategoriaInfo matrizValor = ConfigUtils.categoriasPorPalabra.get(key);

					prod.setCategoria_1(matrizValor.getCategoria());
					prod.setCategoria_2(matrizValor.getSubCategoria());
					prod.setCategoria_3(matrizValor.getSubSubCategoria());

					if (prod.getSubtitulo().isEmpty())
						prod.setSubtitulo(prod.getMarca());

					if (prod.getDescripcion().isEmpty())
						prod.setDescripcion(prod.getMarca());

					if (!filtrados.containsKey(prod.getId()))
						filtrados.put(prod.getId(),prod);
					done = true;
				}
			}

			if (!done) {
				
				prod.setCategoria_1("");
				prod.setCategoria_2("");
				prod.setCategoria_3("");
				
				if (!filtrados.containsKey(prod.getId()))
					filtrados.put(prod.getId(),prod);
			}

		}
		
		List<String> datos = new ArrayList<>();
		datos.add("Id,Titulo_P,Subtitulo_P,Descripcion,Valor,Marca,Categoria,Subcategoria,Sub_sub_categoria,URL_Redireccion,URL_Imagen_Principal,URL_Imagen_2,URL_Imagen_3,URL_Imagen_4,URL_Imagen_5,Tienda, Stock");
		for (ProductoAbs datoTienda_Gral : filtrados.values()) {
			datos.add(datoTienda_Gral.toCSVLine());
			LOG.creadoPorProductoMatriz(datoTienda_Gral.toCSVLine());
		}
		
		CSVUtils.write(datos,"archivosNuevos.csv");
		CSVUtils.writeInController(datos, LOG.getHistorialCSV(), "archivosNuevos.csv", response);
		
	}

	
	
}
