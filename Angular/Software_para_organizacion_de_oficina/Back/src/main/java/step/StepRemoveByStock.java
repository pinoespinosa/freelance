package step;

import java.util.ArrayList;
import java.util.List;

import tiendas.ProductoAbs;
import utils.LOG;

public class StepRemoveByStock {

	/**
	 * Retorna un lista con los productos que pasan la validacion del stock
	 * @param productos
	 * @return
	 */
	public static List<ProductoAbs> doStep(List<ProductoAbs> productos) {
		LOG.log("Eliminando datos por Stock ...");

		List<ProductoAbs> filtrados = new ArrayList<>();
		for (ProductoAbs datoTienda : productos) {
			if (!"Agotado".equals(datoTienda.getStock()) && !"Sin stock".equals(datoTienda.getStock())
					&& !"Consultar".equals(datoTienda.getStock()))
				filtrados.add(datoTienda);
			else
				LOG.descartadoStock(datoTienda.getId());
		}
				
		return filtrados;
	}

}
