package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import data.Cliente;
import data.Info;
import data.Pago;
import data.Requerimiento;
import data.Trabajo;

public class DataSourceReal implements IDataSource {

	private Info obj;

	public DataSourceReal() {
		readFromFile();
	}

	void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			obj = mapper.readValue(new File("file.json"), Info.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> getClientes() {
		
		if (obj==null){
			throw new RuntimeException("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		}
		
		return obj.getClientes();
	}

	@Override
	public Cliente createCliente(Cliente c) {
		c.setId(System.currentTimeMillis()+"");
		c.setTrabajos(new ArrayList<>());
		obj.getClientes().add(c);
		return c;
	}

	@Override
	public List<String> getUniversidadesList() {
		return obj.getUniversidades();
	}

	@Override
	public void createUniversidad(String universidad) {
		obj.getUniversidades().add(universidad);
		infoToFile(obj, "file.json");
	}
	

	@Override
	public void createTrabajo(String idCliente, Trabajo trabajo) {
		Cliente c = new Cliente();
		c.setId(idCliente);

		Cliente original = obj.getClientes().get(obj.getClientes().indexOf(c));
		trabajo.setId(c.getId() + "-" + original.getTrabajos().size());
		
		
		original.getTrabajos().add(trabajo);
		infoToFile(obj, "file.json");
	}

	@Override
	public void importCSV(MultipartFile filename) {

		Info base = new Info();
		base.setClientes(new ArrayList<>());
		Hashtable<String, Cliente> clientes = new Hashtable<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		

		int cant = -1;
		
		try {
			List<String> archivo = CSVUtils.readCsvFile(filename);

			for (String fila : archivo) {

				try {
					cant++;
					String[] valores = fila.split(CSVUtils.sep_column);

					String dato_nombre = valores[1].trim();

					Cliente c = null;
					if (!clientes.containsKey(dato_nombre)) {
						c = new Cliente();
						c.setId(cant+"");
						c.setNombre(dato_nombre);
						c.setFechaSuscripcion(valores[0].trim());
						try {	c.setTelefono1(valores[2].split("/")[0].trim());	} catch (Exception e) {	}
						try {	c.setTelefono2(valores[2].split("/")[1].trim());	} catch (Exception e) {	}
						try {	c.setTelefono3(valores[2].split("/")[2].trim());	} catch (Exception e) {	}

						try {	c.setEmail1(valores[3].split(",")[0].trim());	} catch (Exception e) {	}
						try {	c.setEmail2(valores[3].split(",")[1].trim());	} catch (Exception e) {	}
						try {	c.setEmail3(valores[3].split(",")[2].trim());	} catch (Exception e) {	}

						c.setTrabajos(new ArrayList<>());
						base.getClientes().add(c);
						clientes.put(dato_nombre, c);
					} else {
						c = clientes.get(dato_nombre);
					}

					Trabajo t = new Trabajo();
					t.setId(c.getId() + "-" + c.getTrabajos().size());
					t.setTema(valores[4].trim());
					
					if (Strings.isNullOrEmpty(t.getTema()))
						t.setTema("Sin datos");
					
					t.setMonto(valores[13].trim());

					String carrera = valores[5].trim();
					if (!base.getCarreras().contains(carrera))
						base.getCarreras().add(carrera);
					t.setCarrera(carrera);
					
					String univ = valores[6].trim();
					if (!base.getUniversidades().contains(univ))
						base.getUniversidades().add(univ);
					t.setUniversidad(univ);
									
					String dondeSeEntero = valores[7].trim();
					if (!base.getDondeSeEntero().contains(dondeSeEntero))
						base.getDondeSeEntero().add(dondeSeEntero);
					t.setDondeSeEntero(dondeSeEntero);

					t.setAsesor(valores[10].trim());
					t.setFecha(valores[0].trim());
					t.setFecha_entrega(valores[8].trim());
					t.setEstado("Desconocido");
					
					t.setPagos(new ArrayList<>());
					
					try {
						Pago p = new Pago();
						p.setFecha_pago(valores[0].trim());
						p.setAbono(valores[14].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}

					try {

						Pago p = new Pago();
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[0].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[16].trim())));	} catch (Exception e) {}
						p.setAbono(valores[15].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}
					
					try {

						Pago p = new Pago();
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[0].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[16].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[18].trim())));	} catch (Exception e) {}
						p.setAbono(valores[17].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}
					try {
						
						float pagado = 0;
						for (Pago valor : t.getPagos()) {
							pagado += Float.parseFloat(valor.getAbono());
						}
						float saldo = Float.parseFloat(t.getMonto()) - pagado;	
						t.setSaldo(saldo+"");
						
					} catch (Exception e) {
						// TODO: handle exception
					}

						
					c.getTrabajos().add(t);

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			System.out.println(archivo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		infoToFile(base, "file.json");
		obj=base;
	}

	@Override
	public Cliente getCliente(String idCliente) {
		
		Cliente c = new Cliente();
		c.setId(idCliente);
		
		return obj.getClientes().get((obj.getClientes().indexOf(c)));
	}

	@Override
	public Trabajo getTrabajo(String idCliente, String idTrabajo) {
		
		Cliente c = new Cliente();
		c.setId(idCliente);
		Cliente clie = obj.getClientes().get((obj.getClientes().indexOf(c)));
		
		Trabajo t = new Trabajo();
		t.setId(idTrabajo);
		Trabajo trab = clie.getTrabajos().get((clie.getTrabajos().indexOf(t)));
		
		return trab;
	}

	@Override
	public Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado) {

		Trabajo trabajo = getTrabajo(clienteID, trabajoID);
		trabajo.setEstado(estado);
		return trabajo;
	}

	@Override
	public List<String> getCarrerasList() {
		return obj.getCarreras();
	}

	@Override
	public List<String> getDondeSeEnteroList() {
		return obj.getDondeSeEntero();
	}

	@Override
	public void addRequerimiento(String idCliente, String idTrabajo, Requerimiento requerimiento) {
		Trabajo t = getTrabajo(idCliente, idTrabajo);
		t.getRequerimientos().add(requerimiento);
	}

	@Override
	public Trabajo updateFechaEntrega(String clienteID, String trabajoID, String fechaNueva) {
		Trabajo t = getTrabajo(clienteID, trabajoID);
		t.setFecha_entrega(fechaNueva);
		infoToFile(obj, "file.json");
		
		return t;
	}

	@Override
	public List<Cliente> getClientNuevosList(Date fechaDesde, Date fechaHasta) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<Cliente> filtrados = new ArrayList<>();

		for (Cliente c : obj.getClientes()) {

			Cliente m = c.clone();
						
			for (Trabajo t : m.getTrabajos()) {

				try {

					List<Pago> fil = new ArrayList<>();

					for (Pago p : t.getPagos()) {

						if (sdf.parse(p.getFecha_pago()).before(fechaHasta)
								&& sdf.parse(p.getFecha_pago()).after(fechaDesde)) {
							fil.add(p);
						}

					}

					t.setPagos(fil);

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			List<Trabajo> filTrab = new ArrayList<>();
			for (Trabajo t : m.getTrabajos()) {
				if (!t.getPagos().isEmpty())
				{
					filTrab.add(t);
				}
			}
			m.setTrabajos(filTrab);
			
			if (!m.getTrabajos().isEmpty())
				filtrados.add(m);
			
		}
		
		
		return filtrados;

	}



}
