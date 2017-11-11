package datasource;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Trabajo;

public interface IDataSource {

	List<Cliente> getClientes();
		
	Cliente createCliente(Cliente c);

	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

	void createTrabajo(String idCliente, Trabajo trabajo);

	void importCSV(MultipartFile filename);

	Cliente getCliente(String idCliente);

	Trabajo getTrabajo(String idCliente, String idTrabajo);

	Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado);

	List<String> getCarrerasList();

	List<String> getDondeSeEnteroList();

}
