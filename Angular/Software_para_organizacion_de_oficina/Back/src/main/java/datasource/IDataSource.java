package datasource;

import java.util.List;

import data.Cliente;

public interface IDataSource {

	List<Cliente> getClientes();
	
	void createCliente(Cliente c);

	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

}
