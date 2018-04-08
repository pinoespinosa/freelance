package data;

import static org.hamcrest.Matchers.instanceOf;

public class UsuarioActa implements Comparable<UsuarioActa>{

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

	@Override
	public int compareTo(UsuarioActa o) {
		return o.getUserID().compareTo(this.getUserID());
	}

	@Override
	public boolean equals(Object obj) {

		System.out.println();
		
		if (obj instanceof UsuarioActa ){
			
			UsuarioActa user = (UsuarioActa) obj;
			
			return user.getUserID().equals(this.getUserID());
			
			
		}
		
		return super.equals(obj);
	}

	
	
}
