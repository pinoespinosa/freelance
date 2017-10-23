package datasource;

import java.util.ArrayList;
import java.util.List;

import data.Cliente;

public class DataSourceMocked implements IDataSource {

	@Override
	public List<Cliente> getClientes() {

		List<Cliente> list = new ArrayList<>();
		Cliente test = new Cliente();
		test.setApellido("Espinosa");
		test.setNombre("Andres");
		test.setEmail1("pino@test.com");
		list.add(test);
		return list;
	}

	@Override
	public void createCliente(Cliente c) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getUniversidadesList() {

		List<String> lista = new ArrayList<>();
		lista.add("Univ. Nac. Del Centro de la Prov. Bs. As.");
		lista.add("Universidad Catolica de Chile");
		lista.add("Universidad Fasta");

		return lista;
	}

	@Override
	public void createUniversidad(String universidad) {
		// TODO Auto-generated method stub
		
	}

}
