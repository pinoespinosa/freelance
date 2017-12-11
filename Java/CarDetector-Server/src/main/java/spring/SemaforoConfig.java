package spring;

public class SemaforoConfig {

	private int tiempo_verde_1 = 10;
	private int tiempo_verde_2 = 10;
	private int tiempo_amarillo_1 = 2;
	private int tiempo_amarillo_2 = 2;
	private int tiempo_rojo_doble = 2;

	public int getTiempo_verde_1() {
		return tiempo_verde_1;
	}

	public void setTiempo_verde_1(int tiempo_verde_1) {
		this.tiempo_verde_1 = tiempo_verde_1;
	}

	public int getTiempo_verde_2() {
		return tiempo_verde_2;
	}

	public void setTiempo_verde_2(int tiempo_verde_2) {
		this.tiempo_verde_2 = tiempo_verde_2;
	}

	public int getTiempo_amarillo_1() {
		return tiempo_amarillo_1;
	}

	public void setTiempo_amarillo_1(int tiempo_amarillo_1) {
		this.tiempo_amarillo_1 = tiempo_amarillo_1;
	}

	public int getTiempo_amarillo_2() {
		return tiempo_amarillo_2;
	}

	public void setTiempo_amarillo_2(int tiempo_amarillo_2) {
		this.tiempo_amarillo_2 = tiempo_amarillo_2;
	}

	public int getTiempo_rojo_doble() {
		return tiempo_rojo_doble;
	}

	public void setTiempo_rojo_doble(int tiempo_rojo_doble) {
		this.tiempo_rojo_doble = tiempo_rojo_doble;
	}

}
