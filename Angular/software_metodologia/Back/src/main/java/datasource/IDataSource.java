package datasource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
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
import data.UsuarioActa;

public interface IDataSource {

	void importCSV(MultipartFile filename);

	String audit(Auditoria audit);

	List<Auditoria> getAuditoria();

	Auth auth(String email, String pass);
	
	Auth authSuper(String email, String pass, String empresaID);

	Auth editUser(String user, String pass, Rol rol);

	void exportCSV(HttpServletResponse servletResponse);

	void initBD();

	List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID, String token);

	CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado user, String token);

	CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user, String empresaID);

	CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID, String empresaID);

	List<Acta> getActaList(String cuerpoColegiadoID, String empresaID);

	Acta createActa(String cuerpoColegiadoID, String empresaID, Acta acta, String email, boolean sendEmail) throws UnsupportedEncodingException, AddressException;

	Acta editActa(String cuerpoColegiadoID, Acta user, String empresaID);

	Acta getLastActa(String cuerpoColegiadoID, String empresaID);

	List<Tema> getTemaAbiertoList(String cuerpoColegiadoID, String empresaID);

	Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID, String actaID, List<String> cuerpoColList, String comm);

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

	Acta closeActa(String cuerpoColegiadoID, String actaID, String empresaID, Acta acta, boolean sendEmail) throws AddressException, UnsupportedEncodingException, IOException, MessagingException;

	void importJSON(MultipartFile file) throws IOException;

	List<Empresa> listEmpresa(String token);

	Empresa updateEmpresa(Empresa empresa);

	List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID);

	List<Acta> getActaFiltroMente(String filtroMente, String empresaID);

	List<Tema> getTemaListConsulta2(String actaID, String empresaID, String token);

	Auth changePassword(String userEmail, String passNueva, String passNueva2);

	String crearFile(MultipartFile file) throws IllegalStateException, IOException;

	void initBDSampleData();

	void sendEmail(boolean valor);

	Boolean getSendEmail();

	List<String> getEstrategias(String empresa);

	List<String> createEstrategia(String estrategia, String string);

	List<String> quitarEstrategia(String estrategia, String empresaID);

	List<String> getIndicador(String empresaID);

	List<String> createIndicador(String indicador, String empresaID);

	List<String> quitarIndicador(String indicador, String empresaID);

	Empresa getEmpresa(String id);

	List<Auth> usuariosEmpresaList(String empresaId);

	Acta editActaReunion(Acta acta, String empresaID);

	List<UsuarioActa> getResponsables(String empresaID);

	List<Tarea> getActaFiltrada(String empresaID, String responsableId, String cuerpoColegiadoId);

	List<Tarea> getTareaDia(String empresaID, String responsableId, String cuerpoColegiadoId, String dia);

	
	List<Tema> getTareaFiltradaPorTemas(String empresaID, String cuerpoColegiado, String actaId, String estrategiasId);



}
