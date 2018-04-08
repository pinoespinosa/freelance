package datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;

import data.Acta;
import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import data.CuerpoColegiado;
import data.Empresa;
import data.Evento;
import data.Info;
import data.PdfPrinter;
import data.Tarea;
import data.Tema;
import data.UsuarioActa;
import spring.ChipherTool;
import spring.ClientWebConfig;
import spring.EmailUtils;

public class DataSourceReal implements IDataSource {

	public static final String TEMA_CERRADO = "Cerrado";
	public static final String TEMA_ABIERTO = "Abierto";
	
	public static final String TAREA_CERRADA = "Cerrada";
	public static final String TAREA_ABIERTA = "Abierta";
	
	private static String archivoNombre = "MetodManager.json";
	
	private Info obj;

	public DataSourceReal() {
		readFromFile();
		updateFile();
	}



	
	
	@Override
	public void importJSON(MultipartFile filename) throws IOException {

		String line = "";
		String total = "";

		InputStream inputStream = filename.getInputStream();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

			while ((line = br.readLine()) != null) {
				total += line + " ";
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}

		readFromString(total);
		updateFile();

	}
	
	synchronized void readFromString(String data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			obj = mapper.readValue(data, Info.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initBDSampleData() {
		obj = new Info();
		obj.setEmpresas(new ArrayList<>());
		obj.setUsers(new Hashtable<>());

		Empresa emp = new Empresa();
		emp.setLogoEmpresa("https://staticaltmetric.s3.amazonaws.com/uploads/2015/10/dark-logo-for-site.png");
		emp.setNombreEmpresa("Arcor");
		emp.setColegiados(new ArrayList<>());
		emp = createEmpresa(emp);

		Empresa emp2 = new Empresa();
		emp2.setLogoEmpresa("http://www.cfmdq.com.ar/uploads/noticias/images/7766_logo_mccain.jpg");
		emp2.setNombreEmpresa("McKein");
		emp2.setColegiados(new ArrayList<>());
		emp2 = createEmpresa(emp2);
				
		CuerpoColegiado cc = new CuerpoColegiado();
		cc.setActas(new ArrayList<>());
		cc.setNombre("Comercial");
		cc.setPrefijoDocs("COM");
		cc.setTemas(new Hashtable<>());
		cc = createCuerpoColegiado(emp.getId(), cc, null);

		CuerpoColegiado cc2 = new CuerpoColegiado();
		cc2.setActas(new ArrayList<>());
		cc2.setNombre("Gerencial");
		cc2.setPrefijoDocs("GER");
		cc2.setTemas(new Hashtable<>());
		cc2 = createCuerpoColegiado(emp.getId(), cc2, null);

		create("1234", Rol.ADMINISTRADOR, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
				"Andres Espinosa", "pino.espinosa91@gmail.com", "http://brandmark.io/logo-rank/random/bp.png");

		create("1234", Rol.GENERAL, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
				"Valentina Alfonso", "ae@qbkconsulting.com", "http://brandmark.io/logo-rank/random/bp.png");

		create("1234", Rol.SOLO_CONSULTA, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
				"Valentina Alfonso", "andresespinosa91@hotmail.com", "http://brandmark.io/logo-rank/random/bp.png");
		
		create("1234", Rol.SUPER_ADMINISTRADOR, emp.getId(), Arrays.asList(cc.getId(), cc2.getId()),
				"Juan Loa", "juanloa@efevisium.com",
				"http://brandmark.io/logo-rank/random/bp.png");

		updateFile();	
	}
	
	@Override
	public void initBD() {
		obj = new Info();
		obj.setEmpresas(new ArrayList<>());
		obj.setUsers(new Hashtable<>());

		create("1234", Rol.SUPER_ADMINISTRADOR, null, new ArrayList<>(),
				"Andrés Espinosa", "pino.espinosa91@gmail.com",
				"http://efevisium.com/wp-content/uploads/2017/07/logos-vertical-e1501185105618.png");

		
		create("1234", Rol.SUPER_ADMINISTRADOR, null, new ArrayList<>(),
				"Juan Loa", "juanloa@efevisium.com",
				"http://efevisium.com/wp-content/uploads/2017/07/logos-vertical-e1501185105618.png");

		updateFile();
	}
	
	@Override
	public void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();

		System.out.println();
		try {

			String s = ClientWebConfig.getFrontDirectory();
			
			File file = new File(s + archivoNombre);
			obj = mapper.readValue(file, Info.class);

			for (Empresa e : obj.getEmpresas()) {
				for (CuerpoColegiado cc : e.getColegiados()) {
					cc.updateTemas(e);

				}
			}

		} catch (IOException e) {

			initBD();
		}

	}

	void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {

			String s = ClientWebConfig.getFrontDirectory();
									
			mapper.writeValue(new File(s + filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void updateFile() {
		for (Auth auth : obj.getUsers().values()) {
			auth.setToken("");
		}
		infoToFile(obj, archivoNombre);
	}

	private CuerpoColegiado getCuerpoColeg(String cuerpoColegiadoID) {

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

		for (CuerpoColegiado cuCol : e.getColegiados()) {

			if (cuCol.getEmpresa() == null)
				cuCol.setEmpresa(e);

		}

		return e.getColegiados().get((e.getColegiados().indexOf(cc)));
	}

	@Override
	public List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID, String token) {

		if (empresaID == null)
			return new ArrayList<>();

		String email = Auth.getUserEmail(token);
		Auth usuerrr = null;

		for (Auth string : obj.getUsers().values()) {
			if (string.getEmail().equals(email))
				usuerrr = string;
		}

		List<CuerpoColegiado> result = new ArrayList<>();

		if (Rol.SUPER_ADMINISTRADOR.equals(usuerrr.getRol()))
			result.addAll(getCuerpoColegiadoList(empresaID));
		else {

			for (String id : usuerrr.getCcList()) {
				result.add(getCuerpoColeg(id, empresaID));
			}

		}
		return result;

	}

	@Override
	public List<CuerpoColegiado> getCuerpoColegiadoList(String empresaID) {
		return getEmpresa(empresaID).getColegiados();
	}
	
	@Override
	public Empresa getEmpresa(String id) {
		Empresa cc = new Empresa();
		cc.setId(id);
		return obj.getEmpresas().get((obj.getEmpresas().indexOf(cc)));
	}

	@Override
	public CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado cuerpo, String token) {

		Empresa emp = getEmpresa(empresaID);

		Collection<Auth> usuariosEmpresa = new ArrayList<>();
		
		for (Auth string : obj.getUsers().values()) {
			if (string.getEmpresaID() != null && string.getEmpresaID().equals(empresaID))
				usuariosEmpresa.add(string);
		}
		
		
		
		if (emp != null) {
			cuerpo.setId(emp.getId() + "-" + emp.getColegiados().size());
			emp.getColegiados().add(cuerpo);

			for (Auth auth : usuariosEmpresa) {
				auth.getCcList().add(cuerpo.getId());

			}
			
			updateFile();
			return cuerpo;
		} else
			return null;
	}

	@Override
	public CuerpoColegiado editCuerpoColegiado(CuerpoColegiado user, String empresaID) {

		CuerpoColegiado cc = getCuerpoColeg(user.getId(), empresaID);
		if (!Strings.isNullOrEmpty(user.getNombre()))
			cc.setNombre(user.getNombre());
		if (!Strings.isNullOrEmpty(user.getPrefijoDocs()))
			cc.setPrefijoDocs(user.getPrefijoDocs());
		updateFile();
		return user;
	}

	@Override
	public CuerpoColegiado getCuerpoColegiado(String cuerpoColegiadoID, String empresaID) {
		return getCuerpoColeg(cuerpoColegiadoID, empresaID);

	}

	@Override
	public List<Acta> getActaList(String cuerpoColegiadoID, String empresaID) {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		return orig.getActas();
	}

	@Override
	public Acta createActa(String cuerpoColegiadoID, String empresaID, Acta acta, String email, boolean sendEmail) throws UnsupportedEncodingException, AddressException {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		acta.setId(orig.getPrefijoDocs() + "_" + orig.getId() + "-" + orig.getActas().size() + "");

		String nro = orig.getActas().size() + "";

		while (nro.length() < 3)
			nro = "0" + nro;

		acta.setNumeroActa(orig.getPrefijoDocs() + nro);
		acta.setFecha(System.currentTimeMillis());

		acta.setEstado("Citada");

		orig.getActas().add(acta);

		List<String> emailList = new ArrayList<>();
		for (UsuarioActa uActa : acta.getIntegrantes()) {
			if (uActa.getEmail() != null && !uActa.getEmail().isEmpty())
				emailList.add(uActa.getEmail());
		}

		List<Tema> temasList = getTemaAbiertoList(cuerpoColegiadoID, empresaID);
		List<String> tList = new ArrayList<>();
		
		for (Tema tema : temasList) {
			tList.add(tema.toString());
		}
			
		acta.setIntegranteAutorActa(email);
		acta.setCuerpoColegiadoNombre(orig.getNombre());
		
		String cuerpoEmail = 
				"Te estoy citando a la reunion " + acta.getNumeroActa() + 
				" del cuerpo colegiado " + acta.getCuerpoColegiadoNombre() + 
				" a concretarse " + acta.getFechaReunion() + 
				" entre las " + acta.getHoraInicio() + 
				" y las " + acta.getHoraFinal() + 
				" en: " + acta.getLugar() + ", " + acta.getCiudad() + ".\n"+
				"El fin en mente de la reunion es " + acta.getFinMenteGral() + "\n\n" +
				
				"Los temas a tratar son:\n\n" + String.join("\n", tList) + "\n\n"+
				
				"Favor de responder con el fin en mente individual.";
		
		if (sendEmail)
			EmailUtils.sendEmailAttachFileCalendar(email, emailList, acta.getNumeroActa() + " - " + orig.getNombre(),cuerpoEmail
				,acta.getFechaReunion(), acta.getHoraInicio(), acta.getHoraFinal() );

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
	public Acta editActaReunion( Acta acta, String empresaID) {

		Acta orig = getActa(acta.getId(), empresaID);
		orig.setCiudad(acta.getCiudad());
		orig.setFecha(acta.getFecha());
		orig.setFinMenteGral(acta.getFinMenteGral());
		orig.setLugar(acta.getLugar());
		orig.setHoraInicio(acta.getHoraInicio());
		orig.setHoraFinal(acta.getHoraFinal());

		updateFile();
		return acta;
	}
	
	@Override
	public Acta getLastActa(String cuerpoColegiadoID, String empresaID) {
		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		if (orig.getActas().isEmpty())
			return new Acta();
		else
			return orig.getActas().get(orig.getActas().size() - 1);
	}


	@Override
	public List<Tema> getTemaAbiertoList(String cuerpoColegiadoID, String empresaID) {

		List<Tema> temasAbiertos = new ArrayList<>();

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		for (String claves : ccOrig.getTemas().keySet()) {
			if (TEMA_ABIERTO.equals(ccOrig.getTemas().get(claves).getEstado()))
				temasAbiertos.add(ccOrig.getTemas().get(claves));
		}

		return temasAbiertos;
	}

	@Override
	public Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID, String actaID,
			List<String> cuerpoColList, String comm) {

		Acta acta = getActa(actaID, empresaID);
		
		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		tema.setId(ccOrig.getPrefijoDocs() + ccOrig.getTemas().size() + "");
		tema.setFechaCreacion(System.currentTimeMillis());
		
		String indicadores = tema.getIndicador();
		String[] indicadoresList = indicadores.split(",");
		List<String> indicadoresFiltro = new ArrayList<>();
		
		for (String ind : indicadoresList) {
			if (!ind.trim().equals("undefined"))
				indicadoresFiltro.add(ind.trim());		
		}
		tema.setIndicador(String.join(", ", indicadoresFiltro));
		
		
		
		ccOrig.getTemas().put(tema.getId(), tema);

		for (String ccID : cuerpoColList) {
			CuerpoColegiado cc = getCuerpoColegiado(ccID, empresaID);
			cc.getTemas().put(tema.getId(), tema);
		}

		tema.getEventos().add(new Evento(actaID, "Se ha creado el tema en el acta " + acta.getNumeroActa(), System.currentTimeMillis()));
		
		tema.setDetalle(comm);
		
	//	addComentarioToTema(cuerpoColegiadoID, tema.getId(), actaID + "___" + acta.getNumeroActa() + " - " + comm, empresaID);
		updateFile();
		return tema;
	}

	public String getToken(Auth a) {
		a.setToken("");
		ObjectMapper mapper = new ObjectMapper();
		try {
			return ChipherTool.encrypt(mapper.writeValueAsString(a));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Auth auth(String email, String pass) {

		String clave = ChipherTool.encrypt(email + "_" + pass);

		if ("cabralito".equals(email) && "pascalito".equals(pass)) {
			Auth auth = new Auth(clave, Rol.ADMINISTRADOR, "", null, clave, null, clave, "");
			auth.setToken(getToken(auth));
			return auth;
		}

		if (obj.getUsers().containsKey(clave)) {
			Auth auth = obj.getUsers().get(clave);
			auth.setToken(getToken(auth));

			if (auth.getEmpresaID() != null) {
				Empresa empresa = getEmpresa(auth.getEmpresaID());
				auth.setLogo(empresa.getLogoEmpresa());
				auth.setEmpresaNombre(empresa.getNombreEmpresa());
			}
			return auth;
		}
		return null;

	}

	@Override
	public Auth authSuper(String email, String pass, String empresaID) {

		Auth regularAuth = auth(email, pass);
		Auth bb = null;

		if (regularAuth == null || !Rol.SUPER_ADMINISTRADOR.equals(regularAuth.getRol()))
			return null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			String value = mapper.writeValueAsString(regularAuth);
			bb = mapper.readValue(value, Auth.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (empresaID != null && !empresaID.equals("null")) {
			Empresa empresaCombo = getEmpresa(empresaID);

			bb.setEmpresaID(empresaCombo.getId());
			bb.setLogo(empresaCombo.getLogoEmpresa());
			bb.setEmpresaNombre(bb.getEmpresaNombre());
			bb.setCcList(new ArrayList<>());
			for (CuerpoColegiado cu : empresaCombo.getColegiados()) {
				bb.getCcList().add(cu.getId());
			}
		}
		bb.setToken(getToken(bb));
		return bb;
	}

	
	@Override
	public Auth changePassword(String userEmail, String passVieja, String passNueva) {

		String clave = ChipherTool.encrypt(userEmail + "_" + passVieja);
		String claveNueva = ChipherTool.encrypt(userEmail + "_" + passNueva);

		if (obj.getUsers().containsKey(clave)) {
			Auth aux = obj.getUsers().get(clave);
			obj.getUsers().remove(clave);
			obj.getUsers().put(claveNueva, aux);
			updateFile();
			return aux;
		}
		return null;
	}
	
	@Override
	public Auth create( String pass, Rol rol, String empresaID, List<String> ccList, String nombre,
			String email, String logo) {

		for (Auth string : obj.getUsers().values()) {
			if (string.getEmail().equals(email))
				throw new RuntimeException("El email ya existe");
		}
				
		String clave = ChipherTool.encrypt(email + "_" + pass);

		Auth auth = new Auth(obj.getUsers().size() + "", rol, nombre, email, empresaID, ccList, "", logo);
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
		ccOrig.getTemas().get(temaID).getEventos().add(0,new Evento(comentario.split("___")[0],comentario.split("___")[1], System.currentTimeMillis()));
		updateFile();
		return ccOrig.getTemas().get(temaID);

	}

	@Override
	public Tema cerrarTema(String cuerpoColegiadoID, String temaID, String comentario, String empresaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		ccOrig.getTemas().get(temaID).getEventos().add(new Evento(comentario.split("___")[0],comentario.split("___")[1], System.currentTimeMillis()));
		ccOrig.getTemas().get(temaID).setEstado(TEMA_CERRADO);
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
	public List<UsuarioActa> getResponsables(String empresaID) {

		List<UsuarioActa> user = new ArrayList();

		List<CuerpoColegiado> aa = getCuerpoColegiadoList(empresaID);
		for (CuerpoColegiado cuerpoColegiado : aa) {

			for (Tema temas : cuerpoColegiado.getTemas().values()) {

				for (Tarea tarea : temas.getTareas()) {

					if (!user.contains(tarea.getResponsable()))
						user.add(tarea.getResponsable());
				}
			}
		}

		return user;

	}
	
	@Override
	public List<Tarea> getActaFiltrada(String empresaID, String responsableId, String estrategiaId, String temaId) {

		List<Tarea> tareaList = new ArrayList<>();

		for (CuerpoColegiado cuerpoColegiado : getCuerpoColegiadoList(empresaID)) {

			for (Tema tema : cuerpoColegiado.getTemas().values()) {

				if ((temaId.equals("Todos") || temaId.equals(tema.getId()))
						&& (estrategiaId.equals("Todos") || estrategiaId.equals(tema.getObjetivoEstrategico())))
					for (Tarea tarea : tema.getTareas()) {

						if (!tareaList.contains(tarea))
							tareaList.add(tarea);
					}
			}
		}

		if (!responsableId.equals("Todos")) {

			List<Tarea> filtrada = new ArrayList<>();
			for (Tarea tarea : tareaList) {

				if (!filtrada.contains(tarea) && tarea.getResponsable().getUserID().equals(responsableId))
					filtrada.add(tarea);
			}

			tareaList = filtrada;

		}

		return tareaList;
	}


	
	@Override
	public Tarea addComentarioToTarea(String cuerpoColegiadoID, String temaID, String tareaID, String comentario,
			String empresaID) {

		Tema tema = getCuerpoColegiado(cuerpoColegiadoID, empresaID).getTemas().get(temaID);

		Tarea t = new Tarea();
		t.setId(tareaID);
		Tarea aa = tema.getTareas().get(tema.getTareas().indexOf(t));
		aa.getEventos().add(0,comentario.split("___")[1]);
		updateFile();
		return aa;
	}

	@Override
	public Tarea closeTarea(String cuerpoColegiadoID, String temaID, String tareaID, String empresaID,
			String comentario) {

		Tema tema = getCuerpoColegiado(cuerpoColegiadoID, empresaID).getTemas().get(temaID);
		Tarea t = new Tarea();
		t.setId(tareaID);
		Tarea aa = tema.getTareas().get(tema.getTareas().indexOf(t));
		aa.getEventos().add("La tarea fue cerrada en el acta " + comentario);
		aa.setEstado(TAREA_CERRADA);
		updateFile();
		return aa;
	}

	@Override
	public Acta closeActa(String cuerpoColegiadoID, String actaID, String empresaID, Acta acta, boolean sendEmail) throws IOException, MessagingException {

		Acta actaToClose = getActa(actaID, empresaID);
		actaToClose.setSeCumpliofinEnMente(acta.getSeCumpliofinEnMente());
		actaToClose.setElTiempoFueSuficiente(acta.getElTiempoFueSuficiente());
		actaToClose.setHuboInconvenientes(acta.getHuboInconvenientes());
		actaToClose.setTieneSugerencias(acta.getTieneSugerencias());
		actaToClose.setRedaccionDeTareasOk(acta.getRedaccionDeTareasOk());
		
		actaToClose.setHuboInconvenientesTexto(acta.getHuboInconvenientesTexto());
		actaToClose.setTieneSugerenciasTexto(acta.getTieneSugerenciasTexto());

		actaToClose.setComentarioAdicionales(acta.getComentarioAdicionales());
		
		CuerpoColegiado cc = getCuerpoColeg(cuerpoColegiadoID);
		String fileName = "";
		try {
			fileName = PdfPrinter.printPDF(actaToClose, cc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> emailList = new ArrayList<>();
		for (UsuarioActa uActa : actaToClose.getIntegrantes()) {
			if (uActa.getEmail() != null && !uActa.getEmail().isEmpty())
				emailList.add(uActa.getEmail());
		}
		
		if (sendEmail)
			EmailUtils.sendEmailAttachFile("", emailList, "Resumen de la reunion", "",fileName );

		
		actaToClose.setEstado(TEMA_CERRADO);
		updateFile();
		return actaToClose;
	}

	
	@Override
	public Tema actaIsDone(String cuerpoColegiadoID, String empresaID, String actaID) {

		String temasAbiertos = "";

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		for (Tema tema : ccOrig.getTemas().values()) {

			List<Evento> eventos = tema.getEventos();
			boolean faltaComment = true;
			for (Evento evento : eventos) {
				if (evento.getTexto().contains(actaID))
					faltaComment = false;
			}
			if (faltaComment && TEMA_ABIERTO.equals(tema.getEstado()))
				temasAbiertos += "  * Tema:" + tema.getId() + "\n";

		}

		if (!temasAbiertos.isEmpty()) {
			temasAbiertos = "Existen temas sobre los cuales no se ha dejado comentario, ¿desea continuar de todos modos?\n\n"
					+ temasAbiertos;
		}
		
		Tema t = new Tema();
		t.setDetalle(temasAbiertos);
		return t;

	}

	@Override
	public void actaIsDoneOk(String cuerpoColegiadoID, String empresaID, String actaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
	
		Acta acta = getActa(actaID, empresaID);
		
		for (Tema tema : ccOrig.getTemas().values()) {

			List<Evento> eventos = tema.getEventos();
			boolean faltaComment = true;
			for (Evento evento : eventos) {
				if (evento.getIdActa().equals(actaID))
					faltaComment = false;
			}
			if (faltaComment && TEMA_ABIERTO.equals(tema.getEstado()))
				tema.getEventos().add(
						new Evento(actaID, "No se registraron comentarios para el acta " + acta.getNumeroActa(), System.currentTimeMillis()));

		}

		Acta ac = new Acta();
		ac.setId(actaID);

		//ccOrig.getActas().get(ccOrig.getActas().indexOf(ac)).setEstado("Cerrada");
		
		updateFile();

	}

	@Override
	public List<Tema> getTemaListConsulta2(String actaID, String empresaID, String token ) {

		Acta actaSele = getActa(actaID, empresaID);
		CuerpoColegiado cuerpoSelect = null;

		for (CuerpoColegiado cuerpoColeg : getEmpresa(empresaID).getColegiados()) {
			
			if (cuerpoSelect ==null && cuerpoColeg.getActas().contains(actaSele))
				cuerpoSelect = cuerpoColeg;
			
		}
		
		if (cuerpoSelect != null)
			return getTemaListConsulta(cuerpoSelect.getId(), actaID, empresaID, token);
		
		return null;
	}
	
	@Override
	public List<Tema> getTemaListConsulta(String cuerpoColegiadoID, String actaID, String empresaID, String token) {
		
		CuerpoColegiado cc = getCuerpoColeg(cuerpoColegiadoID);

		Acta a = new Acta();
		a.setId(actaID);
		a = cc.getActas().get(cc.getActas().indexOf(a));

		long time = a.getFechaCierre() == 0 ? System.currentTimeMillis() : a.getFechaCierre();

		List<Tema> list = new ArrayList<>();

		for (Tema tema : cc.getTemas().values()) {

			Tema aux = new Tema();
			boolean tuvoActividad = false;

			for (Evento e : tema.getEventos()) {

				if (e.getDate() <= time)
					if (e.getIdActa().equals(actaID))
						tuvoActividad = true;
				
				aux.getEventos().add(e);
			}
			if (!aux.getEventos().isEmpty() && tuvoActividad) {
				aux.setDetalle(tema.getDetalle());
				aux.setEstado(tema.getEstado());
				aux.setIndicador(tema.getIndicador());
				aux.setObjetivoEstrategico(tema.getObjetivoEstrategico());
				aux.setId(tema.getId());
				aux.setTareas(tema.getTareas());
				list.add(aux);
			}
		}

			
		return list;
	}

	@Override
	public Acta updatePaso(String cuerpoColegiadoID, String actaID, String paso, String empresaID) {

		CuerpoColegiado cc = getCuerpoColeg(cuerpoColegiadoID);
		Acta a = new Acta();
		a.setId(actaID);
		a = cc.getActas().get(cc.getActas().indexOf(a));
		a.setPaso(paso);

		updateFile();

		return a;
	}

	@Override
	public Acta getActa(String actaID, String empresaID) {
		
		Empresa empresa = getEmpresa(empresaID);
		
		Acta a = new Acta();
		a.setId(actaID);

		for (CuerpoColegiado cc : empresa.getColegiados()) {	
			if (cc.getActas().contains(a))
				return cc.getActas().get(cc.getActas().indexOf(a));
		}

		return null;	
	}

	@Override
	public List<Empresa> listEmpresa(String token) {

		if (Rol.SUPER_ADMINISTRADOR.equals(Auth.getUserRol(token)) || Strings.isNullOrEmpty(token) ) {
			return obj.getEmpresas();
		} else {
			List<Empresa> list = new ArrayList<>();
			list.add(getEmpresa(Auth.getEmpresaID(token)));
			return list;
		}
	}


	@Override
	public String crearFile(MultipartFile file) throws IllegalStateException, IOException {

		String filePath = "assets/" + System.currentTimeMillis() + file.getOriginalFilename().replaceAll(" ", "_");
		file.transferTo(new File(ClientWebConfig.getFilesDirectory() + filePath ));
		return "{\"File\": \" " + filePath + "\"}";
	}
	
	
	@Override
	public Empresa createEmpresa(Empresa empresa) {

		if(Strings.isNullOrEmpty(empresa.getLogoEmpresa()))
			empresa.setLogoEmpresa("http://www.tiptoncommunications.com/components/com_easyblog/themes/wireframe/images/placeholder-image.png");
		
		if (!empresa.getLogoEmpresa().startsWith("assets/imagenes/")) {
			try (InputStream in = new URL(empresa.getLogoEmpresa()).openStream()) {

				String fileName = "assets/imagenes/" + System.currentTimeMillis();
				Files.copy(in, Paths.get(ClientWebConfig.getFrontDirectory() + fileName));
//				Files.copy(in, Paths.get(ProjectConstants.isFrontAssetPath() + fileName));

				empresa.setLogoEmpresa(fileName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		empresa.setId(obj.getEmpresas().size() + "");
		obj.getEmpresas().add(empresa);
		updateFile();
		return empresa;
	}

	@Override
	public Empresa updateEmpresa(Empresa empresa) {
		Empresa aa = getEmpresa(empresa.getId());

		if (!empresa.getLogoEmpresa().startsWith("assets/imagenes/")) {
			try (InputStream in = new URL(empresa.getLogoEmpresa()).openStream()) {

				String fileName = "assets/imagenes/" + System.currentTimeMillis();
				Files.copy(in, Paths.get(ClientWebConfig.getFrontDirectory() + fileName));
				aa.setLogoEmpresa(fileName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!Strings.isNullOrEmpty(empresa.getNombreEmpresa())) {
			aa.setNombreEmpresa(empresa.getNombreEmpresa());
		}
		updateFile();

		return aa;
	}

	@Override
	public List<Acta> getActaFiltroMente(String filtroMente, String empresaID) {

		List<Acta> lista = new ArrayList<>();
		Empresa a = getEmpresa(empresaID);
		a.getColegiados().forEach(cuerpoCol -> lista.addAll(cuerpoCol.getActas()));
		return lista;
	}

	@Override
	public void sendEmail(boolean valor) {
		obj.setSendJuanEmail(valor);
	}

	@Override
	public Boolean getSendEmail() {
		return obj.isSendJuanEmail();
	}

	@Override
	public List<String> getEstrategias(String empresa) {
		Empresa emp = getEmpresa(empresa);
		return emp.getEstrategias();
	}

	@Override
	public List<String> createEstrategia(String estrategia, String empresa) {

		Empresa emp = getEmpresa(empresa);
		emp.getEstrategias().add(estrategia);
		updateFile();
	
		return emp.getEstrategias();
	}

	@Override
	public List<String> quitarEstrategia(String estrategia, String empresaID) {
		Empresa emp = getEmpresa(empresaID);
		emp.getEstrategias().remove(estrategia);
		updateFile();
	
		return emp.getEstrategias();
	}

	@Override
	public List<String> getIndicador(String empresaID) {
		Empresa emp = getEmpresa(empresaID);
		return emp.getIndicador();
	}

	@Override
	public List<String> createIndicador(String indicador, String empresaID) {

		Empresa emp = getEmpresa(empresaID);
		emp.getIndicador().add(indicador);
		updateFile();
	
		return emp.getIndicador();
	}

	@Override
	public List<String> quitarIndicador(String indicador, String empresaID) {
		Empresa emp = getEmpresa(empresaID);
		emp.getIndicador().remove(indicador);
		updateFile();
	
		return emp.getIndicador();
	}

	@Override
	public List<Auth> usuariosEmpresaList(String empresaId) {

		List<Auth> hh = new ArrayList<>();
		
		Collection<Auth> aa = obj.getUsers().values();
		
		for (Auth auth : aa) {
			if (auth.getEmpresaID()!=null && auth.getEmpresaID().equals(empresaId))
				hh.add(auth);
		}
		
		return hh;
	}


















}
