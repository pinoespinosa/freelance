package classes;

import java.util.Hashtable;
import java.util.List;

public class Tienda {

	private Hashtable<Articulo, Integer> articulosEnStock;
	private Hashtable<Articulo, Integer> articuloOrdenados;


	private List<Venta> ventas;


	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Hashtable<Articulo, Integer> getArticulosEnStock() {
		return articulosEnStock;
	}

	public void setArticulosEnStock(Hashtable<Articulo, Integer> articulosEnStock) {
		this.articulosEnStock = articulosEnStock;
	}

	public Hashtable<Articulo, Integer> getArticuloOrdenados() {
		return articuloOrdenados;
	}

	public void setArticuloOrdenados(Hashtable<Articulo, Integer> articuloOrdenados) {
		this.articuloOrdenados = articuloOrdenados;
	}



}
