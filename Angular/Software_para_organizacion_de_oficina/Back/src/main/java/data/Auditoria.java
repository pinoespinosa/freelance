package data;

public class Auditoria {

	private String id;
	private String fecha;
	private String usuario;
	private String accion;
	private String rol;

	
	public Auditoria() {
	}

	public Auditoria(String id, String fecha, String usuario, String accion, String rol) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.usuario = usuario;
		this.accion = accion;
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
