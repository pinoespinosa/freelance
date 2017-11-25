package datasource;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import data.Auditoria;
import data.Auth;
import data.Auth.Rol;

public interface IDataSource {


	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

	void importCSV(MultipartFile filename);

	List<String> getCarrerasList();

	List<String> getDondeSeEnteroList();


	Hashtable<String, List<String>> getLastSellByTime(int cantidadDias);

	List<Float> getLastSellByTimeCash(int cantidadDias, int cantidadValores);

	List<Float>  getSellsCashByTimeNewClients(int cantidadDias, int cantidadValores);

	List<Float>  getSellsCashByTimeOldClients(int cantidadDias, int cantidadValores);

	List<Float>  getSellsAmmountByTimeNewClients(int cantidadDias, int cantidadValores);

	void exportCSV(HttpServletResponse servletResponse);

	void createCarrera(String carrera);

	void createDondeEntero(String dondeEntero);

	Auth auth(String user, String pass);

	Auth create(String user, String pass, Rol rol);


	Auth editUser(String user, String pass, Rol rol);

	String audit(Auditoria audit);

	List<Auditoria> getAuditoria();
	
}
