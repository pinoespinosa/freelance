package data;

import java.util.ArrayList;
import java.util.List;

public class Acta {

	private String id;
	private String numeroActa;

	private long fecha;
	private long fechaCierre;

	private String lugar;
	private String ciudad;

	private String paso;

	
	private String estado;

	private List<UsuarioActa> integrantes;

	private String finMenteGral;

	private List<Comentario> temas;

	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Acta.class)) {
			Acta t = (Acta) obj;
			return t.getId().equals(getId());
		}
		return false;
	}
	
	public Acta() {
		fechaCierre = 0;
	}

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
		if (integrantes == null)
			integrantes = new ArrayList<>();
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

	public long getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(long fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getPaso() {
		return paso;
	}

	public void setPaso(String paso) {
		this.paso = paso;
	}

}
