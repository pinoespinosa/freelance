package data;

import java.util.List;

public class Cliente {

	private String id;

	private String nombre;
	private String apellido;

	private String email1;
	private String email2;
	private String email3;
	private String email4;

	private String telefono1;
	private String telefono2;
	private String telefono3;
	private String telefono4;

	private List<Trabajo> trabajos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getEmail4() {
		return email4;
	}

	public void setEmail4(String email4) {
		this.email4 = email4;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getTelefono3() {
		return telefono3;
	}

	public void setTelefono3(String telefono3) {
		this.telefono3 = telefono3;
	}

	public String getTelefono4() {
		return telefono4;
	}

	public void setTelefono4(String telefono4) {
		this.telefono4 = telefono4;
	}

	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Cliente.class)) {
			Cliente t = (Cliente) obj;
			return t.getId().equals(getId());
		}
		return false;
	}

}
