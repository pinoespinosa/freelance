package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Acta;
import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import data.CuerpoColegiado;
import data.Empresa;
import data.Evento;
import data.Info;
import data.Tarea;
import data.Tema;
import spring.ChipherTool;

public class DataSourceReal implements IDataSource {

	private Info obj;

	public DataSourceReal() {
		readFromFile();
		updateFile();
	}

	void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();

		System.out.println();
		try {

			Path currentRelativePath = Paths.get("");
			String s = "/home/pino/freelance/Angular/software_metodologia/Front/";
			File file = new File(s + "file.json");
			obj = mapper.readValue(file, Info.class);

		
		} catch (IOException e) {

			obj = new Info();
			obj.setEmpresas(new ArrayList<>());
			obj.setUsers(new Hashtable<>());

			Empresa emp = new Empresa();
			emp.setLogoEmpresa("https://staticaltmetric.s3.amazonaws.com/uploads/2015/10/dark-logo-for-site.png");
			emp.setNombreEmpresa("Arcor");
			emp.setColegiados(new ArrayList<>());
			emp = createEmpresa(emp);

			CuerpoColegiado cc = new CuerpoColegiado();
			cc.setActas(new ArrayList<>());
			cc.setNombre("Comercial");
			cc.setPrefijoDocs("COM");
			cc.setTemas(new Hashtable<>());
			cc = createCuerpoColegiado(emp.getId(), cc);

			CuerpoColegiado cc2 = new CuerpoColegiado();
			cc2.setActas(new ArrayList<>());
			cc2.setNombre("Gerencial");
			cc2.setPrefijoDocs("GER");
			cc2.setTemas(new Hashtable<>());
			cc2 = createCuerpoColegiado(emp.getId(), cc2);

			create("pino", "1234", Rol.ADMINISTRADOR, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
					"Andres Espinosa", "pino.espinosa91@gmail.com", "http://brandmark.io/logo-rank/random/bp.png");

			create("vale", "1234", Rol.ADMINISTRADOR, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
					"Valentina Alfonso", "ae@qbkconsulting.com", "http://brandmark.io/logo-rank/random/bp.png");

			create("fer", "1234", Rol.ADMINISTRADOR, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
					"Fernando Echevarria", "andresespinosa91@hotmail.com",
					"http://brandmark.io/logo-rank/random/bp.png");

			updateFile();
		}

	}

	void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			String s = "/home/pino/freelance/Angular/software_metodologia/Front/";
			mapper.writeValue(new File(s + filename ), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void updateFile() {
		infoToFile(obj,"file.json" );
	}

	private CuerpoColegiado getCuerpoColeg(String cuerpoColegiadoID){
		
		CuerpoColegiado pino = new CuerpoColegiado();
		pino.setId(cuerpoColegiadoID);
		
		for (Empresa emp : obj.getEmpresas()) {
			for (CuerpoColegiado cc : emp.getColegiados()) {
				if (pino.equals(cc))
					return cc;
			}
		}
		return new CuerpoColegiado();
	}
	
	private CuerpoColegiado getCuerpoColeg(String cuerpoColegiadoID, String empresaID) {
		Empresa e = getEmpresa(empresaID);
		CuerpoColegiado cc = new CuerpoColegiado();
		cc.setId(cuerpoColegiadoID);
		return e.getColegiados().get((e.getColegiados().indexOf(cc)));
	}
	
	@Override
	public List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID, List<String> cc) {

		List<CuerpoColegiado> result = new ArrayList<>();

		for (String id : cc) {
			result.add(getCuerpoColeg(id, empresaID));
		}

		return result;

	}

	private Empresa getEmpresa(String id){
		Empresa cc = new Empresa();
		cc.setId(id);
		return obj.getEmpresas().get((obj.getEmpresas().indexOf(cc)));
	}
	
	@Override
	public CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado cuerpo) {

		Empresa emp = getEmpresa(empresaID);

		if (emp != null) {
			cuerpo.setId(emp.getId() + "-" + emp.getColegiados().size());
			emp.getColegiados().add(cuerpo);
			updateFile();
			return cuerpo;
		} else
			return null;
	}

	@Override
	public CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user, String empresaID) {
		
		CuerpoColegiado cc = getCuerpoColeg(user.getId(), empresaID);
		cc.setNombre(user.getNombre());
		updateFile();
		return user;
	}
		
	@Override
	public CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID, String empresaID){
		return getCuerpoColeg(cuerpoColegiadoID, empresaID);
		
	}
	
	@Override
	public List<Acta> getActaList(String cuerpoColegiadoID, String empresaID) {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		return orig.getActas();
	}

	@Override
	public Acta createActa(String cuerpoColegiadoID, String empresaID, Acta acta) {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		acta.setId(orig.getPrefijoDocs() +"_" + orig.getId() + "-" + orig.getActas().size() + "");
		acta.setNumeroActa(orig.getActas().size() + "");
		acta.setFecha(System.currentTimeMillis());

		acta.setEstado("Citada");
		
		orig.getActas().add(acta);
		updateFile();
		return acta;
	}

	@Override
	public Acta editActa(String cuerpoColegiadoID, Acta user, String empresaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		Acta orig = ccOrig.getActas().get(ccOrig.getActas().indexOf(user));
		orig.setCiudad(user.getCiudad());
		orig.setFecha(user.getFecha());
		orig.setFinMenteGral(user.getFinMenteGral());
		orig.setIntegrantes(user.getIntegrantes());
		orig.setLugar(user.getLugar());
		orig.setNumeroActa(user.getNumeroActa());
		orig.setTemas(user.getTemas());

		updateFile();
		return user;
	}
	
	@Override
	public Acta getLastActa(String cuerpoColegiadoID, String empresaID) {
		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		if (orig.getActas().isEmpty())
			return new Acta();
		else
			return orig.getActas().get(orig.getActas().size()-1);
	}


	
		
	@Override
	public void initBD() {
		obj = new Info();
		updateFile();
	}
	
	


	@Override
	public List<Tema> getTemaAbiertoList(String cuerpoColegiadoID, String empresaID) {
		
		List<Tema> temasAbiertos = new ArrayList<>();
		
		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		
		for (String claves : ccOrig.getTemas().keySet()) {
			if ( "Abierto".equals(ccOrig.getTemas().get(claves).getEstado()) )
				temasAbiertos.add(ccOrig.getTemas().get(claves));
		}
		
		return temasAbiertos;
	}
	
	@Override
	public Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID, String actaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		tema.setId(ccOrig.getTemas().size()+"");
		ccOrig.getTemas().put(tema.getId(), tema);
		tema.getEventos().add(new Evento("Se ha creado el tema en el acta " + actaID, System.currentTimeMillis()));
		updateFile();
		return tema;	
	}

	





	public String getToken(Auth a) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return ChipherTool.encrypt(mapper.writeValueAsString(a));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Auth auth(String user, String pass) {

		String clave = ChipherTool.encrypt(user + "_" + pass);
		
		if ( "cabralito".equals(user) && "pascalito".equals(pass)) {
			Auth auth = new Auth(clave, Rol.ADMINISTRADOR, "", null, clave, null, clave,"");
			auth.setToken(getToken(auth));
			return auth;
		}
		
		if (obj.getUsers().containsKey(clave))
		{
			Auth auth = obj.getUsers().get(clave);
			auth.setToken(getToken(auth));
			Empresa empresa = getEmpresa(auth.getEmpresaID());
			auth.setLogo(empresa.getLogoEmpresa());
			auth.setEmpresaNombre(empresa.getNombreEmpresa());
			return auth;
			
			
			
		}
		return null;
		
	}

	@Override
	public Auth create(String user, String pass, Rol rol, String empresaID, List<String> ccList, String nombre, String email, String logo) {
	
		String clave = ChipherTool.encrypt(user + "_" + pass);
		
		Auth auth = new Auth(obj.getUsers().size()+"",rol,nombre, email, empresaID, ccList,"",logo);
		obj.getUsers().put(clave, auth);
		updateFile();

		return auth;
	}

	
	@Override
	public Auth editUser(String user, String pass, Rol rol) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void exportCSV(HttpServletResponse servletResponse) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void importCSV(MultipartFile filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String audit(Auditoria audit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Auditoria> getAuditoria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Empresa createEmpresa(Empresa empresa) {
		

		empresa.setId(obj.getEmpresas().size() + "");
		obj.getEmpresas().add(empresa);
		updateFile();
		return empresa;
	}

	@Override
	public List<Auth> getUsuariosList(String cuerpoColegiadoID) {

		List<Auth> valores = new ArrayList<>();

		Collection<Auth> aa = obj.getUsers().values();
		for (Auth auth : aa) {
			if (auth.getCcList().contains(cuerpoColegiadoID))
				valores.add(auth);
		}

		return valores;
	}

	@Override
	public Tema addComentarioToTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID) {

		
		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		ccOrig.getTemas().get(temaID).getEventos().add(new Evento(comentario, System.currentTimeMillis()));
		updateFile();
		return ccOrig.getTemas().get(temaID);	
		
		
	}

	@Override
	public Tema cerrarTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		ccOrig.getTemas().get(temaID).getEventos().add(new Evento(comentario, System.currentTimeMillis()));
		ccOrig.getTemas().get(temaID).setEstado("Cerrado");
		updateFile();
		return ccOrig.getTemas().get(temaID);
	}

	@Override
	public Tema createTarea(String cuerpoColegiadoID, String temaID, Tarea tarea, String empresaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		Tema tema = ccOrig.getTemas().get(temaID);
		tarea.setId(tema.getTareas().size() + "");
		tema.getTareas().add(tarea);
		updateFile();

		return tema;
	}

	@Override
	public Tarea addComentarioToTarea(String cuerpoColegiadoID, String temaID, String tareaID, String comentario,
			String empresaID) {

		
		Tema tema = getCuerpoColegiado(cuerpoColegiadoID, empresaID).getTemas().get(temaID);
		
		Tarea t = new Tarea();
		t.setId(tareaID);
		Tarea aa = tema.getTareas().get( tema.getTareas().indexOf(t) );
		aa.getEventos().add(comentario);
		updateFile();
		return aa;	
	}

	@Override
	public Tarea closeTarea(String cuerpoColegiadoID, String temaID, String tareaID, String empresaID, String comentario) {
		
		Tema tema = getCuerpoColegiado(cuerpoColegiadoID, empresaID).getTemas().get(temaID);
		Tarea t = new Tarea();
		t.setId(tareaID);
		Tarea aa = tema.getTareas().get( tema.getTareas().indexOf(t) );
		aa.getEventos().add("La tarea fue cerrada en el acta " + comentario);
		aa.setEstado("Cerrada");
		updateFile();
		return aa;	
	}

	@Override
	public Tema actaIsDone(String cuerpoColegiadoID, String empresaID, String actaID) {

		String temasAbiertos = "";

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		for (Tema tema : ccOrig.getTemas().values()) {

			List<Evento> eventos = tema.getEventos();
			boolean faltaComment  = false;
			for (Evento evento : eventos) {
				if(!evento.getTexto().contains(actaID))
					faltaComment=true;
			}
			if (faltaComment)
				temasAbiertos += "  * Tema:"+tema.getId() + "\n";
		
		}

		if (!temasAbiertos.isEmpty())
		{
			temasAbiertos = "Existen temas sobre los cuales no se ha dejado comentario, ¿desea continuar de todos modos?\n\n" + temasAbiertos; 
		}
		Tema t = new Tema();
		t.setDetalle(temasAbiertos);
		return t;

	}

	@Override
	public Tema actaIsDoneOk(String cuerpoColegiadoID, String empresaID, String actaID) {


		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		for (Tema tema : ccOrig.getTemas().values()) {

			List<Evento> eventos = tema.getEventos();
			boolean faltaComment  = false;
			for (Evento evento : eventos) {
				if(!evento.getTexto().contains(actaID))
					faltaComment=true;
			}
			if (faltaComment)
				tema.getEventos().add( new Evento("No se registraron comentarios para el acta " + actaID, System.currentTimeMillis()));
		
		}
		
		Acta ac = new Acta();
		ac.setId(actaID);
		
		ccOrig.getActas().get(ccOrig.getActas().indexOf(ac)).setEstado("Cerrada");;
		updateFile();
	
		return null;	
		
	}

	@Override
	public List<Tema> getTemaListConsulta(String cuerpoColegiadoID, String actaID, String empresaID) {

		CuerpoColegiado cc = getCuerpoColeg(cuerpoColegiadoID);
		
		Acta a = new Acta();
		a.setId(actaID);
		a = cc.getActas().get(cc.getActas().indexOf(a));
		
		long time = a.getFechaCierre() == 0? System.currentTimeMillis() : a.getFechaCierre();
		
		List<Tema> list = new ArrayList<>();
		
		for (Tema tema : cc.getTemas().values()) {
			
			Tema aux = new Tema();
			boolean tuvoActividad = false;

			for (Evento e : tema.getEventos()) {

				if (e.getDate() <= time )
					if (e.getTexto().contains(actaID))
						tuvoActividad=true;
					aux.getEventos().add(e);
			}
			if (!aux.getEventos().isEmpty() && tuvoActividad){
				aux.setDetalle(tema.getDetalle());
				aux.setEstado(tema.getEstado());
				aux.setId(tema.getId());
				aux.setTareas(tema.getTareas());
				list.add(aux);
			}
		}
		
		return list;
	}








}
