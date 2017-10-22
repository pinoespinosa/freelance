package step;

import java.sql.SQLException;

import utils.BD;
import utils.LOG;

public class StepDisableProduct {


	public static void doStep(String tienda) throws SQLException {
		LOG.log("Desactivando productos ...");
		BD.executeSqlVoid("UPDATE " + BD.get_BD_NAME() + ".productos SET estado = '0' WHERE tienda = '" + tienda + "';");
	}
	
}
