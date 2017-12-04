package classes;

import java.util.ArrayList;
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
		if (articuloOrdenados==null)
			articuloOrdenados = new ArrayList<>();
		return articuloOrdenados;
	}

	public void setArticuloOrdenados(List<ItemVenta> articuloOrdenados) {
		this.articuloOrdenados = articuloOrdenados;
	}

	public List<ItemVenta> getArticulosEnStock() {
		if (articulosEnStock==null)
			articulosEnStock = new ArrayList<ItemVenta>();
		return articulosEnStock;
	}

	public void setArticulosEnStock(List<ItemVenta> articulosEnStock) {
		this.articulosEnStock = articulosEnStock;
	}

	public List<Proveedor> getProveedores() {
		if (proveedores==null)
			proveedores=new ArrayList<>();
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}




}
