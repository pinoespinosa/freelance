package csvMarket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.JSchException;

import step.StepCreateValidateProducts;
import step.StepDisableProduct;
import step.StepRemoveByKey;
import step.StepRemoveByStock;
import step.StepUpdatePrice;
import tiendas.ProductoAbs;
import utils.BD;
import utils.CSVUtils;
import utils.ConfigUtils;
import utils.LOG;

public class DurekaService {

	static List<ProductoAbs> rechazados = new ArrayList<>();

	public static Set<String> setArchivosDesc = new HashSet<String>();
	public static List<ProductoAbs> rechazadosProcesados = new ArrayList<>();

	public static void crearProductosValidadosPorUsuario(MultipartFile file, HttpServletResponse response)
			throws SQLException, NumberFormatException, ClassNotFoundException {

		try {
			StepCreateValidateProducts.doStep(file);
			String text = "";
			for (Object value : setArchivosDesc.toArray()) {
				text += value.toString() + "\n";
			}

			try {
				
		        String bashComand = ConfigUtils.getConfigDatos().get("command1").replace("{{listaArchivos}}","\"" + text + "\""); 	
				BD.executeBash(text, bashComand);
				
				BD.executeBash(text, ConfigUtils.getConfigDatos().get("command2"));
				BD.executeBash(text, ConfigUtils.getConfigDatos().get("command3"));
				BD.executeBash(text, ConfigUtils.getConfigDatos().get("command4"));
				BD.executeBash(text, ConfigUtils.getConfigDatos().get("command5"));

				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			}
		
			
			CSVUtils.writeInController(LOG.getHistorialCreadosUser(), "archivosNuevos.csv", response);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<ProductoAbs> processFile(MultipartFile file) throws SQLException, IOException {

		List<ProductoAbs> listaProd = new ArrayList<>();

		if (file == null)
			return listaProd;

		rechazados.clear();

		LOG.log("Procesando " + file);

		LOG.log("Leyendo datos CSV ...");
		for (String line : CSVUtils.readCsvFile(file)) {
			if (!line.isEmpty()) {
				ProductoAbs prod = ProductoAbs.create(line);
				if (!listaProd.contains(prod))
					listaProd.add(prod);
				else
					LOG.productoDuplicado(prod.getTitulo());
			}
		}

		if (!listaProd.isEmpty()) {
			StepDisableProduct.doStep(listaProd.get(0).getTiendaID());
			listaProd = StepRemoveByStock.doStep(listaProd);
			listaProd = StepRemoveByKey.doStep(listaProd);
			listaProd = StepUpdatePrice.doStep(listaProd, rechazados);

		}
		List<ProductoAbs> noCreados = ProductFactory.crearProductosTiendaFromProductos(rechazados);

		LOG.log("Agregando productos no creados ...");
		LOG.log("Finalido " + file);
		LOG.log("");
		return noCreados;
	}

}
