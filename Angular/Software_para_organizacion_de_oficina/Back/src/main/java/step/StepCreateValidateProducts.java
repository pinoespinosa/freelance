package step;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import csvMarket.CategoriaInfo;
import csvMarket.DurekaService;
import csvMarket.Script;
import tiendas.ProductoAbs;
import tiendas.TipoProducto;
import utils.BD;
import utils.CSVUtils;
import utils.ConfigUtils;
import utils.LOG;

public class StepCreateValidateProducts {

	public static void doStep(MultipartFile file)
			throws SQLException, NumberFormatException, ClassNotFoundException, IOException {

		LOG.log("Leyendo datos CSV ...");
		List<ProductoAbs> listaProd = new ArrayList<>();

		for (String line : CSVUtils.readCsvFile(file)) {
			if (!line.isEmpty() && !line.startsWith("*")) {
				ProductoAbs prod = ProductoAbs.create(line);
				if (!listaProd.contains(prod)) {

					String keyCat = prod.getCategoria_1() + '_' + prod.getCategoria_2() + '_' + prod.getCategoria_3();
					CategoriaInfo cat = ConfigUtils.getCategoriasPorCat1Cat2Cat3().get(keyCat);

					if (cat != null) {

						TipoProducto tip = new TipoProducto(prod, cat);

						int cant = Integer
								.parseInt(BD.executeSqlQueryString(Script.existTipoProduct(prod.getTitulo()), "cant"));
						if (cant == 0) {
							BD.executeSqlVoid(Script.createTipoProduct(tip));
							LOG.tipoProductoUserCreado("TipoProducto Creado, " + tip.getTitulo());

							int cantProd = Integer.parseInt(
									BD.executeSqlQueryString(Script.existProductByTitle(prod.getTitulo()), "cant"));
							if (cantProd == 0) {

								String script = Script.createProduct(prod, tip);
								BD.executeSqlVoid(script);
								LOG.productoUserCreado("Producto Creado, " + prod.getTitulo());
								
								DurekaService.setArchivosDesc.add(prod.getOriginal_url_imagen_1());
								DurekaService.setArchivosDesc.add(prod.getOriginal_url_imagen_2());
								DurekaService.setArchivosDesc.add(prod.getOriginal_url_imagen_3());
								DurekaService.setArchivosDesc.add(prod.getOriginal_url_imagen_4());
								DurekaService.setArchivosDesc.add(prod.getOriginal_url_imagen_5());
								
							} else
								LOG.productoUserDuplicado("Producto Duplicado en BD, " + prod.getTitulo());

						} else {
							LOG.tipoProductoUserDuplicado("TipoProducto Duplicado en BD, " + tip.getTitulo());
						}
					} else {
						LOG.productoUserDuplicado("Producto no creado, no se hallaron las catergías en la matrix, " + prod.getTitulo());

					}
				}
				else
					LOG.productoUserDuplicado("Producto Duplicado en CSV, " + prod.getTitulo());
			}
		}

	}

}
