package data;

import java.util.List;

public class Usuario {

	private String userID;
	
	private String nombre;
	private String empresaID;

	private List<String> cuerposColegiado;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmpresaID() {
		return empresaID;
	}

	public void setEmpresaID(String empresaID) {
		this.empresaID = empresaID;
	}

	public List<String> getCuerposColegiado() {
		return cuerposColegiado;
	}

	public void setCuerposColegiado(List<String> cuerposColegiado) {
		this.cuerposColegiado = cuerposColegiado;
	}

}
