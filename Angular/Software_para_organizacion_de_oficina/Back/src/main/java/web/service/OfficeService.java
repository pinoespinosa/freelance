package web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Requerimiento;
import data.Trabajo;


public interface OfficeService {

	String getHelloWorld();

	List<Cliente> getClientList();
	
	Cliente createCliente(Cliente cliente);

	List<String> getUniversidList();

	void createUniversidad(String universidad);

	void createTrabajo(String idCliente, Trabajo trabajo);

	void importCSV(MultipartFile file);

	Cliente getClient(String idCliente);

	Trabajo getTrabajo(String idCliente, String idTrabajo);

	Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado);

	List<String> getCarrerasList();

	List<String> getDondeSeEnteroList();

	void addRequerimiento(String idCliente, String idTrabajo, Requerimiento requerimiento);

	Trabajo updateFechaEntrega(String clienteID, String trabajoID, String fechaNueva);

	List<Cliente> getClientNuevosList(String fechaDesde, String fechaHasta);

}
