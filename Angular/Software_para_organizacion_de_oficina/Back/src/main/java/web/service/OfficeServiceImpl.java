package web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.Cliente;
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
	public void createCliente(Cliente c) {
		datasource.createCliente(c);
	}

	@Override
	public List<String> getUniversidList() {
		return datasource.getUniversidadesList();

	}

	@Override
	public void createUniversidad(String universidad) {
		datasource.createUniversidad(universidad);
	}


}
