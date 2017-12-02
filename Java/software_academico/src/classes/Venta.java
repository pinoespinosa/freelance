package classes;

import java.util.Date;
import java.util.List;

public class Venta {

	public Venta(List<ItemVenta> items,long time) {
		super();
		this.items = items;
		this.time = time;
	}

	private List<ItemVenta> items;
	private long time;
	
	public List<ItemVenta> getItems() {
		return items;
	}

	public void setItems(List<ItemVenta> items) {
		this.items = items;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Venta - " + new Date(time).toGMTString();
	}
	
}
