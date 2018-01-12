package data;

public class Evento {

	private String texto;
	private String idActa;
	private long date;

	public Evento() {

	}

	public Evento(String acta, String texto, long date) {
		super();
		this.texto = texto;
		this.date = date;
		this.idActa = acta;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getIdActa() {
		return idActa;
	}

	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}

}
