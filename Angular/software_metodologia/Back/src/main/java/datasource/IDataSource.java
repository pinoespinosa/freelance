package datasource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import data.Acta;
import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import data.CuerpoColegiado;
import data.Tema;
import data.Usuario;

public interface IDataSource {


	List<String> getUniversidadesList();

	void createUniversidad(String universidad);

	void importCSV(MultipartFile filename);


	String audit(Auditoria audit);

	List<Auditoria> getAuditoria();

	List<CuerpoColegiado> getCuerpoColegiadoList();

	Auth auth(String user, String pass);

	Auth create(String user, String pass, Rol rol);

	Auth editUser(String user, String pass, Rol rol);

	CuerpoColegiado createCuerpoColegiado(CuerpoColegiado user);

	CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user);

	void exportCSV(HttpServletResponse servletResponse);

	void initBD();

	List<Acta> getActaList(String cuerpoColegiadoID);

	Acta createActa(String cuerpoColegiadoID, Acta user);

	Acta editActa(String cuerpoColegiadoID, Acta user);

	Acta getLastActa(String cuerpoColegiadoID);

	List<Usuario> getUsuariosList();

	Usuario createUser(Usuario user);

	List<Tema> getTemaAbiertoList(String cuerpoColegiadoID);

	Tema createTema(String cuerpoColegiadoID, Tema tema);

	CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID);
	
}
