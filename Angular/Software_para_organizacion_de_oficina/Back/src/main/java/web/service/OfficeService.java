package web.service;

import java.util.List;

import data.Cliente;


public interface OfficeService {

	String getHelloWorld();

	List<Cliente> getClientList();
	
	void createCliente(Cliente cliente);

	List<String> getUniversidList();

	void createUniversidad(String universidad);
}
