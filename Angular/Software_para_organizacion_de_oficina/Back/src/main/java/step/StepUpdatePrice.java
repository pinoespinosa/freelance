package step;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csvMarket.Script;
import tiendas.ProductoAbs;
import utils.BD;
import utils.LOG;

public class StepUpdatePrice {

	public static List<ProductoAbs> doStep(List<ProductoAbs> productos, List<ProductoAbs> rechazados )
			throws SQLException {
		LOG.log("Actualizando precios y urls ...");
		LOG.text(" ------------------ ACTUALIZACION DE PRECIOS  ------------------ ");
		List<ProductoAbs> filtrados = new ArrayList<>();

		for (ProductoAbs dato : productos) {

			if (dato.getId() != null && !dato.getId().isEmpty() && dato.getTienda() != null
					&& !dato.getTienda().isEmpty() && dato.getValor() != null && !dato.getValor().isEmpty()) {
				String exist = BD.executeSqlQueryString(Script.existProductByID(dato.getId()), "existe");

				if (!exist.equals("0")) {
					BD.executeSqlVoid(Script.updateProduct(dato.getValorNumero(), dato.getUrl_redireccion(),
							dato.getId(), dato.getTiendaID()));
					filtrados.add(dato);
					LOG.precioUpdate(dato.getTitulo() + " se establecio el precio de ------>" + dato.getValorNumero());
				} else {
					rechazados.add(dato);
				}
			}

		}
		LOG.text("");
		return filtrados;
	}
}
