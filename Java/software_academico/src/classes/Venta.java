package classes;

import java.util.Date;
import java.util.List;

public class Venta implements Comparable<Venta> {

	public Venta(List<ItemVenta> items, long time, String estado, String detalle, String formaPago) {
		super();
		this.items = items;
		this.time = time;
		this.estado = estado;
		this.detalle=detalle;
		this.setFormaPago(formaPago);
	}

	private List<ItemVenta> items;
	private String estado;
	private String detalle;
	private String formaPago;

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
		return "Venta - " + new Date(time).toGMTString() + " [" + estado + " " + formaPago +"]";
	}

	@Override
	public int compareTo(Venta o) {

		if (time == o.getTime())
			return 0;

		if (time > o.getTime())
			return 1;
		else
			return -1;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

}
