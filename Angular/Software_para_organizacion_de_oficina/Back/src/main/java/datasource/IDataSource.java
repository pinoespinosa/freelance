package datasource;

import java.util.List;

import data.Cliente;
import data.Trabajo;

public interface IDataSource {

	List<Cliente> getClientes();
		
	void createCliente(Cliente c);

	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

	void createTrabajo(String idCliente, Trabajo trabajo);

	void importCSV(String filename);

}
