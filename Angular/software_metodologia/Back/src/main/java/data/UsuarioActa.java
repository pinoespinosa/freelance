package data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioActa {

	private String userID;
	private String nombre;
	private String email;
	
	private String finEnMente;
	private String estado;

	
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFinEnMente() {
		return finEnMente;
	}

	public void setFinEnMente(String finEnMente) {
		this.finEnMente = finEnMente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
