package web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Trabajo;
import datasource.IDataSource;

@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	IDataSource datasource;
	
	@Override
	public String getHelloWorld() {
		return "Holaa";
	}

	@Override
	public List<Cliente> getClientList() {

		return datasource.getClientes();
	}

	@Override
	public Cliente createCliente(Cliente c) {
		return datasource.createCliente(c);
	}

	@Override
	public List<String> getUniversidList() {
		return datasource.getUniversidadesList();

	}

	@Override
	public void createUniversidad(String universidad) {
		datasource.createUniversidad(universidad);
	}

	@Override
	public void createTrabajo(String idCliente, Trabajo trabajo) {
		datasource.createTrabajo(idCliente, trabajo);
		
	}

	@Override
	public void importCSV(MultipartFile filename) {
		datasource.importCSV(filename);
	}

	@Override
	public Cliente getClient(String idCliente) {
		return datasource.getCliente(idCliente);
	}

	@Override
	public Trabajo getTrabajo(String idCliente, String idTrabajo) {
		return datasource.getTrabajo(idCliente,idTrabajo);
		
	}

	@Override
	public Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado) {
		return datasource.getChangeTrabajoStatus(clienteID, trabajoID, estado);
	}

	@Override
	public List<String> getCarrerasList() {
		return datasource.getCarrerasList();
	}

	@Override
	public List<String> getDondeSeEnteroList() {
		return datasource.getDondeSeEnteroList();
	}


}
