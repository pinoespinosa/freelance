package data;

public class Evento {

	private String texto;
	private long date;

	public Evento() {

	}

	public Evento(String texto, long date) {
		super();
		this.texto = texto;
		this.date = date;
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

}
