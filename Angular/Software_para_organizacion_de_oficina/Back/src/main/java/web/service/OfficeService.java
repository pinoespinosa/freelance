package web.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import data.Cliente;
import data.Trabajo;


public interface OfficeService {

	String getHelloWorld();

	List<Cliente> getClientList();
	
	void createCliente(Cliente cliente);

	List<String> getUniversidList();

	void createUniversidad(String universidad);

	void createTrabajo(String idCliente, Trabajo trabajo);

	void importCSV(String filename);
}
