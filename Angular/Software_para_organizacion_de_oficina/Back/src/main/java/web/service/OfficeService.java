package web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Trabajo;


public interface OfficeService {

	String getHelloWorld();

	List<Cliente> getClientList();
	
	void createCliente(Cliente cliente);

	List<String> getUniversidList();

	void createUniversidad(String universidad);

	void createTrabajo(String idCliente, Trabajo trabajo);

	void importCSV(MultipartFile file);
}
