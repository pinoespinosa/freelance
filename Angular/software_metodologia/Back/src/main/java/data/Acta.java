package data;

import java.util.ArrayList;
import java.util.List;

public class Acta {

	private String id;
	
	private String numeroActa;
	private long fecha;

	private String lugar;
	private String ciudad;

	private List<UsuarioActa> integrantes;

	private String finMenteGral;
	private String finMenteEsp;

	private List<String> temas;

	
	
	
	
	
	
	
	
	

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



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UsuarioActa> getIntegrantes() {
		if (integrantes==null)
			integrantes=new ArrayList<>();
		return integrantes;
	}

	public void setIntegrantes(List<UsuarioActa> integrantes) {
		this.integrantes = integrantes;
	}

	public List<String> getTemas() {
		return temas;
	}

	public void setTemas(List<String> temas) {
		this.temas = temas;
	}

}
