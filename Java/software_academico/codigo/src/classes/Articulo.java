package classes;

public class Articulo {

	private String nombre;
	private String codigo;
	private float precioCompra;
	private float precioVenta;
	private String proveedorID;

	public Articulo(String codigo, String nombre, float precioCompra, float precioVenta, String proveedor) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.proveedorID = proveedor;

	}
	public Articulo() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return codigo + " " + nombre + " " + precioCompra + " " + precioVenta;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj.getClass().equals(Articulo.class)) {
			Articulo a = (Articulo) obj;

			return nombre.equals(a.getNombre());
		} else
			return false;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProveedor() {
		return proveedorID;
	}

	public void setProveedor(String proveedor) {
		this.proveedorID = proveedor;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public float getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(float precioCompra) {
		this.precioCompra = precioCompra;
	}

}
