package classes;

public class ItemVenta {

	private Articulo articulo;
	private int cantidad;

	public ItemVenta(Articulo articulo, int cantidad) {
		super();
		this.articulo = articulo;
		this.cantidad = cantidad;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return articulo.toString() + " [ " + cantidad + " unid. ]";
	}

	@Override
	public boolean equals(Object obj) {

		if (obj.getClass().equals(ItemVenta.class)) {
			ItemVenta a = (ItemVenta) obj;

			return articulo.equals(a.getArticulo());
		} else
			return false;
	}
}
