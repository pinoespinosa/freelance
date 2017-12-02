package classes;

import java.util.List;

public class Venta {

	public Venta(List<ItemVenta> items) {
		super();
		this.items = items;
	}

	private List<ItemVenta> items;

	public List<ItemVenta> getItems() {
		return items;
	}

	public void setItems(List<ItemVenta> items) {
		this.items = items;
	}
	
}
