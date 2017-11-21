package data;

public class Auth {

	public enum Rol {
		ADMINISTRADOR, JEFE_ADMINIST, JEFE_OPERATIVO,
		GERENTE
	}
	
	private Rol rol;
	private String token;

	public Auth() {
	}
	
	public Auth(Rol rol, String token) {
		this.rol = rol;
		this.token = token;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
