package datasource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.internet.AddressException;
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

	Auth auth(String email, String pass);

	Auth editUser(String user, String pass, Rol rol);

	void exportCSV(HttpServletResponse servletResponse);

	void initBD();

	List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID, List<String> cc);

	CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado user);

	CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user, String empresaID);

	CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID, String empresaID);

	List<Acta> getActaList(String cuerpoColegiadoID, String empresaID);

	Acta createActa(String cuerpoColegiadoID, String empresaID, Acta acta, String email) throws UnsupportedEncodingException, AddressException;

	Acta editActa(String cuerpoColegiadoID, Acta user, String empresaID);

	Acta getLastActa(String cuerpoColegiadoID, String empresaID);

	List<Tema> getTemaAbiertoList(String cuerpoColegiadoID, String empresaID);

	Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID, String actaID, List<String> cuerpoColList);

	Empresa createEmpresa(Empresa empresa);

	List<Auth> getUsuariosList(String cuerpoColegiadoID);

	Auth create(String pass, Rol rol, String empresaID, List<String> ccList, String nombre, String email, String logo);

	Tema addComentarioToTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID);

	Tema cerrarTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID);

	Tema createTarea(String cuerpoColegiadoID, String temaID, Tarea tarea, String empresaID);

	Tarea addComentarioToTarea(String cuerpoColegiadoID, String temaID, String tareaID, String comentario,
			String empresaID);

	Tarea closeTarea(String cuerpoColegiadoID, String temaID, String tareaID, String empresaID, String comentario);

	Tema actaIsDone(String cuerpoColegiadoID, String empresaID, String actaID);

	void actaIsDoneOk(String cuerpoColegiadoID, String empresaID, String actaID);

	List<Tema> getTemaListConsulta(String cuerpoColegiadoID, String actaID, String empresaID);

	Acta updatePaso(String cuerpoColegiadoID, String actaID, String empresaID, String string);

	Acta getActa(String actaID, String empresaID);

	void readFromFile();

	Acta closeActa(String cuerpoColegiadoID, String actaID, String empresaID) throws AddressException, UnsupportedEncodingException, IOException;

	void importJSON(MultipartFile file) throws IOException;

	List<Empresa> listEmpresa();

	Empresa updateEmpresa(Empresa empresa);

	List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID);

	List<Acta> getActaFiltroMente(String filtroMente, String empresaID);

	List<Tema> getTemaListConsulta2(String actaID, String empresaID);

}
