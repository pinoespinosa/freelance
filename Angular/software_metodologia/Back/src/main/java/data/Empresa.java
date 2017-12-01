package data;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String id;
	private List<CuerpoColegiado> colegiados;
	private String nombreEmpresa;
	private String logoEmpresa;

	
	public List<CuerpoColegiado> getColegiados() {
		if (colegiados==null)
			colegiados=new ArrayList<>();
		
		return colegiados;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Empresa.class)) {
			Empresa t = (Empresa) obj;
			return t.getId().equals(getId());
		}
		return false;
	}
	
	public void setColegiados(List<CuerpoColegiado> colegiados) {
		this.colegiados = colegiados;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogoEmpresa() {
		return logoEmpresa;
	}

	public void setLogoEmpresa(String logoEmpresa) {
		this.logoEmpresa = logoEmpresa;
	}

}
