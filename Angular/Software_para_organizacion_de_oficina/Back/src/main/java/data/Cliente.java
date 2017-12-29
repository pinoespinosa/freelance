package data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cliente {
	@JsonIgnoreProperties({"fistWork"})

	private String id;

	private String nombre;
	private String fechaSuscripcion;

	private String email1;
	private String email2;
	private String email3;

	private String telefono1;
	private String telefono2;
	private String telefono3;

	private Boolean estado;

	private Trabajo fistWork;
	
	private List<Trabajo> trabajos;

	public Cliente clone() {

		ObjectMapper mapper = new ObjectMapper();
		try {
			String objeto = mapper.writeValueAsString(this);
			return mapper.readValue(objeto, Cliente.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		if (nombre==null)
			nombre = "";
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail1() {
		if (email1==null)
			email1 = "";
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		if (email2==null)
			email2 = "";
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		if (email3==null)
			email3 = "";
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getTelefono1() {
		if (telefono1==null)
			telefono1 = "";
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		if (telefono2==null)
			telefono2 = "";
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getTelefono3() {
		if (telefono3==null)
			telefono3 = "";
		return telefono3;
	}

	public void setTelefono3(String telefono3) {
		this.telefono3 = telefono3;
	}

	public List<Trabajo> getTrabajos() {
		if (trabajos==null)
			trabajos= new ArrayList<>();
		return trabajos;
	}

	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Cliente.class)) {
			Cliente t = (Cliente) obj;
			return t.getId().equals(getId());
		}
		return false;
	}

	public String getFechaSuscripcion() {
		if (fechaSuscripcion==null)
			fechaSuscripcion = "";
		return fechaSuscripcion;
	}

	public void setFechaSuscripcion(String fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}
	
	public Trabajo getFistWork(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Comparator<Trabajo> comp = new Comparator<Trabajo>() {
			
			@Override
			public int compare(Trabajo o1, Trabajo o2) {

				Date d1=new Date(0);
				try {
					d1 = sdf.parse(o1.getFecha());
				} catch (ParseException e) {
				}
				Date d2=new Date(0);
				try {
					d2 = sdf.parse(o2.getFecha());
				} catch (ParseException e) {
				}
				
				return d1.compareTo(d2);
			}
		};
		
		
		List<Trabajo> ord =this.getTrabajos();
		Collections.sort(ord,comp);
		
		if (!ord.isEmpty())
			return ord.get(0);
		else
			return null;
		
	}

	public void setFistWork(Trabajo fistWork) {
		this.fistWork = fistWork;
	}
	
	public String toCSV() {

		List<String> list = Arrays.asList(getFechaSuscripcion(), getNombre(),
				getTelefono1() + "/" + getTelefono2() + "/" + getTelefono3(),
				getEmail1() + "/" + getEmail2() + "/" + getEmail3());

		return String.join("\";\"", list);

	}

	public boolean getEstado() {
		
		if (estado==null)
			return true;
		
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
