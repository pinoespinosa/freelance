package step;

import java.util.ArrayList;
import java.util.List;

import tiendas.ProductoAbs;
import utils.ConfigUtils;
import utils.LOG;

public class StepRemoveByKey {

	public static List<ProductoAbs> doStep(List<ProductoAbs> productos) {

		LOG.log("Eliminando datos por palabras ...");
		LOG.text(" --------------- DESCARTADOS POR PALABRAS CLAVE --------------- ");

		List<ProductoAbs> filtrados = new ArrayList<>();
		for (ProductoAbs datoTienda : productos) {

			boolean ignorado = false;

			for (String palabra : ConfigUtils.getExcludeWord()) {

				if (!ignorado && datoTienda.getTitulo().toLowerCase().contains(palabra.toLowerCase())) {
					ignorado = true;
					LOG.descartadoKey(datoTienda.getTitulo() + " ------> " + palabra.toLowerCase());
				}
			}

			if (!ignorado)
				filtrados.add(datoTienda);
		}
		LOG.text("");
		return filtrados;
	}
	
}
