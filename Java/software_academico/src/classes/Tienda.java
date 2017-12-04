package classes;

import java.util.List;

public class Tienda {

	private List<ItemVenta> articulosEnStock;
	private List<ItemVenta> articuloOrdenados;
	private List<Proveedor> proveedores;


	private List<Venta> ventas;


	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public List<ItemVenta> getArticuloOrdenados() {
		return articuloOrdenados;
	}

	public void setArticuloOrdenados(List<ItemVenta> articuloOrdenados) {
		this.articuloOrdenados = articuloOrdenados;
	}

	public List<ItemVenta> getArticulosEnStock() {
		return articulosEnStock;
	}

	public void setArticulosEnStock(List<ItemVenta> articulosEnStock) {
		this.articulosEnStock = articulosEnStock;
	}

	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}




}
