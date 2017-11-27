package data;

import java.util.List;

public class Acta {

	private String id;
	
	private String numeroActa;
	private long fecha;

	private String lugar;
	private String ciudad;

	private List<String> integrantes;

	private String finMenteGral;
	private String finMenteEsp;

	private String temasNuevos;
	private String temasTratados;

	
	
	
	
	
	
	
	
	

	public String getNumeroActa() {
		return numeroActa;
	}

	public void setNumeroActa(String numeroActa) {
		this.numeroActa = numeroActa;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<String> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<String> integrantes) {
		this.integrantes = integrantes;
	}

	public String getFinMenteGral() {
		return finMenteGral;
	}

	public void setFinMenteGral(String finMenteGral) {
		this.finMenteGral = finMenteGral;
	}

	public String getFinMenteEsp() {
		return finMenteEsp;
	}

	public void setFinMenteEsp(String finMenteEsp) {
		this.finMenteEsp = finMenteEsp;
	}

	public String getTemasNuevos() {
		return temasNuevos;
	}

	public void setTemasNuevos(String temasNuevos) {
		this.temasNuevos = temasNuevos;
	}

	public String getTemasTratados() {
		return temasTratados;
	}

	public void setTemasTratados(String temasTratados) {
		this.temasTratados = temasTratados;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
