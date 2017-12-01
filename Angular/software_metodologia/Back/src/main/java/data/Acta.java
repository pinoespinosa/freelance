package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Acta {

	private String id;
	
	private String numeroActa;
	private long fecha;

	private String lugar;
	private String ciudad;

	private String estado;
	
	private List<UsuarioActa> integrantes;

	private String finMenteGral;

	private List<Comentario> temas;

	

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

	public List<Comentario> getTemas() {
		return temas;
	}

	public void setTemas(List<Comentario> temas) {
		this.temas = temas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
