package datasource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import data.Acta;
import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import data.CuerpoColegiado;
import data.Empresa;
import data.Tarea;
import data.Tema;

public interface IDataSource {

	void importCSV(MultipartFile filename);

	String audit(Auditoria audit);

	List<Auditoria> getAuditoria();

	Auth auth(String user, String pass);

	Auth editUser(String user, String pass, Rol rol);

	void exportCSV(HttpServletResponse servletResponse);

	void initBD();

	List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID, List<String> cc);

	CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado user);

	CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user, String empresaID);

	CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID, String empresaID);

	List<Acta> getActaList(String cuerpoColegiadoID, String empresaID);

	Acta createActa(String cuerpoColegiadoID, String empresaID, Acta user);

	Acta editActa(String cuerpoColegiadoID, Acta user, String empresaID);

	Acta getLastActa(String cuerpoColegiadoID, String empresaID);

	List<Tema> getTemaAbiertoList(String cuerpoColegiadoID, String empresaID);

	Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID, String actaID);

	Empresa createEmpresa(Empresa empresa);

	List<Auth> getUsuariosList(String cuerpoColegiadoID);

	Auth create(String user, String pass, Rol rol, String empresaID, List<String> ccList, String nombre, String email,
			String logo);

	Tema addComentarioToTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID);

	Tema cerrarTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID);

	Tema createTarea(String cuerpoColegiadoID, String temaID, Tarea tarea, String empresaID);

	Tarea addComentarioToTarea(String cuerpoColegiadoID, String temaID, String tareaID, String comentario,
			String empresaID);

	Tarea closeTarea(String cuerpoColegiadoID, String temaID, String tareaID, String empresaID, String comentario);

	Tema actaIsDone(String cuerpoColegiadoID, String empresaID, String actaID);

	Tema actaIsDoneOk(String cuerpoColegiadoID, String empresaID, String actaID);

	List<Tema> getTemaListConsulta(String cuerpoColegiadoID, String actaID, String empresaID);

}
