package classes;

public class Articulo {

	private String nombre;
	private float precio;
	
	
	
	public Articulo(String nombre, float precio) {
		super();
		this.nombre = nombre;
		this.setPrecio(precio);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return nombre + "... $" + precio;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj.getClass().equals(Articulo.class)) {
			Articulo a = (Articulo) obj;

			return nombre.equals(a.getNombre());
		} else
			return false;
	}

	
}
