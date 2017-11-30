package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Acta;
import data.Auditoria;
import data.Auth;
import data.Auth.Rol;
import spring.ChipherTool;
import data.CuerpoColegiado;
import data.Empresa;
import data.Info;
import data.Tema;
import data.Usuario;

public class DataSourceReal implements IDataSource {

	private Info obj;

	public DataSourceReal() {
		readFromFile();
	}

	void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();
		
			System.out.println();
			 try {
				obj = mapper.readValue(new File("file.json"), Info.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(filename), data);
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
	public CuerpoColegiado createCuerpoColegiado(String empresaID, CuerpoColegiado user) {

		Empresa emp = getEmpresa(empresaID);

		if (emp != null) {
			user.setId(emp.getColegiados().size() + "");
			emp.getColegiados().add(user);
			updateFile();
			return user;
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
		return getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		
	}
	
	@Override
	public List<Acta> getActaList(String cuerpoColegiadoID, String empresaID) {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);
		return orig.getActas();
	}

	@Override
	public Acta createActa(String cuerpoColegiadoID, String empresaID, Acta user) {

		CuerpoColegiado orig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		user.setId(orig.getId() + "-" + orig.getActas().size() + "");
		user.setNumeroActa(orig.getActas().size() + "");
		user.setFecha(System.currentTimeMillis());

		orig.getActas().add(user);
		updateFile();
		return user;
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
	public List<Usuario> getUsuariosList() {
		return obj.getUsuarios();
	}
	

	@Override
	public Usuario createUser(Usuario user) {
		user.setUserID(obj.getUsuarios().size()+"");
		obj.getUsuarios().add(user);
		updateFile();
		return user;
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
	public Tema createTema(String cuerpoColegiadoID, Tema tema, String empresaID) {

		CuerpoColegiado ccOrig = getCuerpoColegiado(cuerpoColegiadoID, empresaID);

		tema.setId(ccOrig.getTemas().size()+"");
		ccOrig.getTemas().put(tema.getId(), tema);
		updateFile();
		return tema;	
	}

	





	
	
	
	@Override
	public Auth auth(String user, String pass) {

		String clave = ChipherTool.encrypt(user + "_" + pass);
		
		if (obj.getUsers().containsKey(clave))
		{
			Auth auth = obj.getUsers().get(clave);
			return new Auth(auth.getRol(), ChipherTool.encrypt(auth.getRol().name() +"_"+ clave));
		}
		return null;
		
	}

	@Override
	public Auth create(String user, String pass, Rol rol) {
		// TODO Auto-generated method stub
		return null;
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
	public List<String> getUniversidadesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUniversidad(String universidad) {
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









}
