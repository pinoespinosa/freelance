package classes;

public class Proveedor {

	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private String RFC;

	public Proveedor() {
	}
	
	public Proveedor(String nombre, String direccion, String telefono, String email, String rFC) {

		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.RFC = rFC;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj.getClass().equals(Proveedor.class)) {
			Proveedor a = (Proveedor) obj;

			return nombre.equals(a.getNombre());
		} else
			return false;
	}

	@Override
	public String toString() {
		return nombre + " " + direccion +" "+ telefono + " " + email + " " + RFC;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRFC() {
		return RFC;
	}

	public void setRFC(String rFC) {
		RFC = rFC;
	}

}
