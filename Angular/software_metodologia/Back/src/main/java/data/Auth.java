package data;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.ChipherTool;

public class Auth {

	public enum Rol {
		ADMINISTRADOR, SUPER_ADMINISTRADOR, GENERAL, SOLO_CONSULTA
	}
	private String userID;

	private Rol rol;
	
	private String nombre;
	private String email;
	private String logo;
	private String empresaID;
	private String empresaNombre;

	private List<String> ccList;
	private String token;

	public Auth() {
	}


	public Auth(String userID, Rol rol, String nombre, String email, String empresaID, List<String> ccList,
			String token, String logo) {
		super();
		this.userID = userID;
		this.rol = rol;
		this.nombre = nombre;
		this.email = email;
		this.empresaID = empresaID;
		this.ccList = ccList;
		this.token = token;
		this.logo = logo;
	}



	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getEmpresaID() {
		return empresaID;
	}

	public void setEmpresaID(String empresaID) {
		this.empresaID = empresaID;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	// --------------------- Decoders ---------------------

	public static String getEmpresaID(String token) {

		Auth obj;
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(ChipherTool.decrypt(token), Auth.class);
			return obj.getEmpresaID();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Rol getUserRol(String token) {

		Auth obj;
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(ChipherTool.decrypt(token), Auth.class);
			return obj.getRol();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static String getUserEmail(String token) {

		Auth obj;
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(ChipherTool.decrypt(token), Auth.class);
			return obj.getEmail();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getCuerpoColegiadosList(String token) {
		Auth obj;
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(ChipherTool.decrypt(token), Auth.class);
			return obj.getCcList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getEmpresaNombre() {
		return empresaNombre;
	}


	public void setEmpresaNombre(String empresaNombre) {
		this.empresaNombre = empresaNombre;
	}

}
