package data;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String id;
	private List<CuerpoColegiado> colegiados;
	private String nombreEmpresa;

	
	public List<CuerpoColegiado> getColegiados() {
		if (colegiados==null)
			colegiados=new ArrayList<>();
		
		return colegiados;
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

}
