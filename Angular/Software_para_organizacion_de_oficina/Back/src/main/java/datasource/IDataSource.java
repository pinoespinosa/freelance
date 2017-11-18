package datasource;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Pago;
import data.Requerimiento;
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

	void addRequerimiento(String idCliente, String idTrabajo, Requerimiento requerimiento);

	Trabajo updateFechaEntrega(String clienteID, String trabajoID, String fechaNueva);

	List<Cliente> getClientNuevosList(Date date, Date date2) throws ParseException;

	Hashtable<String, List<String>> getLastSellByTime(int cantidadDias);

	List<Float> getLastSellByTimeCash(int cantidadDias, int cantidadValores);

	List<Float>  getSellsCashByTimeNewClients(int cantidadDias, int cantidadValores);

	List<Float>  getSellsCashByTimeOldClients(int cantidadDias, int cantidadValores);

	List<Float>  getSellsAmmountByTimeNewClients(int cantidadDias, int cantidadValores);

	void exportCSV();

	void createCarrera(String carrera);

	void createDondeEntero(String dondeEntero);

	Cliente editCliente(Cliente user);

	Pago addPago(String clienteID, String trabajoID, Pago pago);

}
