package data;

public class Pago {

	private String id;
	private String fecha_pago;
	private String abono;
	private String forma_pago;
	private String detalle;

	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForma_pago() {
		if (forma_pago==null)
			return "-";
		
		return forma_pago;
	}

	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}

	public String getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(String fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public String getDetalle() {
		if (detalle==null)
			return "-";
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
