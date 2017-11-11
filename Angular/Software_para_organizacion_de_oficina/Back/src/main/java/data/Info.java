package data;

import java.util.ArrayList;
import java.util.List;

public class Info {

	private List<Cliente> clientes;

	private List<String> universidades;
	private List<String> carreras;
	private List<String> dondeSeEntero;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<String> getCarreras() {
		if (carreras == null)
			carreras = new ArrayList<>();
		return carreras;
	}

	public void setCarreras(List<String> carreras) {
		this.carreras = carreras;
	}

	public List<String> getUniversidades() {

		if (universidades == null)
			universidades = new ArrayList<>();

		return universidades;
	}

	public void setUniversidades(List<String> universidades) {
		this.universidades = universidades;
	}

	public List<String> getDondeSeEntero() {

		if (dondeSeEntero == null)
			dondeSeEntero = new ArrayList<>();

		return dondeSeEntero;
	}

	public void setDondeSeEntero(List<String> dondeSeEntero) {
		this.dondeSeEntero = dondeSeEntero;
	}

}
