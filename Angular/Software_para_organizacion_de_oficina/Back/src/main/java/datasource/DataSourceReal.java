package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Cliente;
import data.Info;
import data.Trabajo;

public class DataSourceReal implements IDataSource {

	private Info obj;

	public DataSourceReal() {
		readFromFile();
	}

	void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			obj = mapper.readValue(new File("file.json"), Info.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void infoToFile() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("file.json"), obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> getClientes() {
		return obj.getClientes();
	}

	@Override
	public void createCliente(Cliente c) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getUniversidadesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUniversidad(String universidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTrabajo(String idCliente, Trabajo trabajo) {
		Cliente c = new Cliente();
		c.setId(idCliente);

		Cliente original = obj.getClientes().get(obj.getClientes().indexOf(c));
		original.getTrabajos().add(trabajo);
		infoToFile();
	}

	@Override
	public void importCSV(String filename) {

		List<String> archivo = CSVUtils.readCsvFile(filename);

	}

}
