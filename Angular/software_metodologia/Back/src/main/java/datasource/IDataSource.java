package datasource;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import data.Cliente;
import data.Pago;
import data.Requerimiento;
import data.Trabajo;

public interface IDataSource {

	List<Cliente> getClientes();
		
	Cliente createCliente(Cliente c);

	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

	Trabajo createTrabajo(String idCliente, Trabajo trabajo);

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

	void exportCSV(HttpServletResponse servletResponse);

	void createCarrera(String carrera);

	void createDondeEntero(String dondeEntero);

	Cliente editCliente(Cliente user);

	Pago addPago(String clienteID, String trabajoID, Pago pago);

	Trabajo editTrabajo(String idCliente, Trabajo user);

	Auth auth(String user, String pass);

	Auth create(String user, String pass, Rol rol);

	List<Cliente> getClientListPendientes();

	Trabajo updateAsesor(String clienteID, String trabajoID, String asesor);

	Auth editUser(String user, String pass, Rol rol);

	String audit(Auditoria audit);

	List<Auditoria> getAuditoria();
	
}
